package io.husaind.service;

import io.husaind.dto.MonteCarloRequest;
import io.husaind.dto.MonteCarloResponse;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MonteCarloService {

    RandomGenerator random;


    @Autowired
    public MonteCarloService() {
        random = new JDKRandomGenerator();
    }


    /**
     * Reference: https://www.youtube.com/watch?v=Q5Fw2IRMjPQ
     *
     * @param request
     * @return
     */
    public MonteCarloResponse simulate(MonteCarloRequest request) {

        //TODO validate request
        //TODO make sure percentages are divided by 100
        request = validateAndCorrectRequest(request);

        //NOTE I have hardcoded aggressive and conservative types. Instead a list of Risk and Return can be accepted and returned
        NormalDistribution aggressiveDistribution = new NormalDistribution(request.getAggressiveReturn(), request.getAggressiveRisk()); //Can pass random, here. Not sure the use
        List<Double> aggressiveValues = simulateTimes(request, aggressiveDistribution);

        //Implementation of the normal (gaussian) distribution.
        NormalDistribution conservativeDistribution = new NormalDistribution(request.getConservativeReturn(), request.getConservativeRisk()); //Can pass random, here. Not sure the use
        List<Double> conservativeValues = simulateTimes(request, conservativeDistribution);

        MonteCarloResponse response = calcResponse(aggressiveValues, conservativeValues);

        return response;

    }

    private MonteCarloRequest validateAndCorrectRequest(MonteCarloRequest request) {
        request.setAggressiveReturn(request.getAggressiveReturn() / 100);
        request.setAggressiveRisk(request.getAggressiveRisk() / 100);
        request.setConservativeReturn(request.getConservativeReturn() / 100);
        request.setConservativeRisk(request.getConservativeRisk() / 100);
        request.setInflationRate(request.getInflationRate() / 100);

        return request;
    }


    private List<Double> simulateTimes(MonteCarloRequest request, NormalDistribution distribution) {
        List<Double> simulatedValues = new ArrayList<>(request.getSimulationCount().intValue());
        for (int i = 0; i < request.getSimulationCount(); i++) {
            simulatedValues.add(yearlyReturn(request, distribution));
        }

        return simulatedValues;
    }

    private Double yearlyReturn(MonteCarloRequest request, NormalDistribution distribution) {

        Double balance = request.getInvestedAmountInitial();

        for (int i = 0; i < request.getInvestedYears(); i++) {
            balance = balance * (1 + distribution.inverseCumulativeProbability(random.nextDouble()));
            balance = balance - request.getInflationRate() * balance; //Adjust for inflation
        }

        return balance;

    }

    //TODO this can be improved by calculating manually instead of using helpers
    private MonteCarloResponse calcResponse(List<Double> aggressiveValues, List<Double> conservativeValues) {

        double[] aggressiveArray = aggressiveValues.stream()
                .mapToDouble(Double::doubleValue)
                .sorted()
                .toArray();

        double[] conservativeArray = conservativeValues.stream()
                .mapToDouble(Double::doubleValue)
                .sorted()
                .toArray();

        Percentile percentile90 = new Percentile(90);
        Percentile percentile50 = new Percentile(50);
        Percentile percentile10 = new Percentile(10);

        return MonteCarloResponse.builder()
                .aggressiveMedian(percentile50.evaluate(aggressiveArray))
                .aggressive90Percentile(percentile90.evaluate(aggressiveArray))
                .aggressive10Percentile(percentile10.evaluate(aggressiveArray))
                .conservativeMedian(percentile50.evaluate(conservativeArray))
                .conservative90Percentile(percentile90.evaluate(conservativeArray))
                .conservative10Percentile(percentile10.evaluate(conservativeArray))
                .build();
    }


    /**
     * Unused
     *
     * @param list
     * @return
     */
    private Double getMedian(List<Double> list) {

        list.sort(Comparator.comparing(Double::doubleValue));
        int midList = list.size() / 2;

        Double median = list.get(midList);
        if (list.size() % 2 == 0) {
            median = (median + list.get(midList - 1)) / 2;
        }

        Double percentile90 = list.get(list.size() * 9 / 10);
        Double percentile10 = list.get(list.size() / 10);

        return median;
    }


}
