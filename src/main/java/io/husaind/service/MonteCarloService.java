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
import java.util.Collections;
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

        //NOTE I have hardcoded aggressive and conservative types. Instead a list of Risk and Return can be accepted and returned
        NormalDistribution aggressiveDistribution = new NormalDistribution(request.getAggressiveReturn(), request.getAggressiveRisk()); //Can pass random, here. Not sure the use
        List<Double> aggressiveValues = simulateTimes(request, aggressiveDistribution);

        NormalDistribution conservativeDistribution = new NormalDistribution(request.getConservativeReturn(), request.getConservativeRisk()); //Can pass random, here. Not sure the use
        List<Double> conservativeValues = simulateTimes(request, conservativeDistribution);

        MonteCarloResponse response = calcResponse(aggressiveValues, conservativeValues);

        return MonteCarloResponse.builder().build();

    }

    //TODO this can be improved by calculating manually instead of using helpers
    private MonteCarloResponse calcResponse(List<Double> aggressiveValues, List<Double> conservativeValues) {

        double[] aggressiveArray = aggressiveValues.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();

        double[] conservativeArray = conservativeValues.stream()
                .mapToDouble(Double::doubleValue)
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

    private Double getMedian(List<Double> simulatedValues) {
        Collections.sort(simulatedValues);
        int middle = simulatedValues.size() / 2;
        Double medianValue; //declare variable
        if (simulatedValues.size() % 2 == 1) {
            medianValue = simulatedValues.get(middle);
        } else {
            medianValue = (simulatedValues.get(middle - 1) + simulatedValues.get(middle)) / 2;
        }

        return medianValue;
    }


    public Double yearlyReturn(MonteCarloRequest request, NormalDistribution distribution) {

        Double balance = request.getInvestedAmountInitial() * (1 + distribution.inverseCumulativeProbability(random.nextDouble())) + request.getInvestedAmountYearly();

        for (int i = 0; i < request.getInvestedYears(); i++) {
            balance = balance * (1 + distribution.inverseCumulativeProbability(random.nextDouble()));
            balance = balance - request.getInflationRate() * balance;
            balance = balance + request.getInvestedAmountYearly(); //Assuming invested end of the year
        }

        //TODO handle inflation

        return balance;

    }

    public List<Double> simulateTimes(MonteCarloRequest request, NormalDistribution distribution) {
        List<Double> simulatedValues = new ArrayList<>(request.getSimulationCount().intValue());
        for (int i = 0; i < request.getSimulationCount(); i++) {
            simulatedValues.add(yearlyReturn(request, distribution));
        }

        return simulatedValues;
    }
}
