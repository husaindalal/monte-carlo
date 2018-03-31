package io.husaind.service;

import io.husaind.dto.MonteCarloRequest;
import io.husaind.dto.MonteCarloResponse;
import io.husaind.dto.Percentiles;
import io.husaind.dto.RiskReturn;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MonteCarloService {

    private RandomGenerator random;


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

        request = validateAndCorrectRequest(request);

        MonteCarloResponse response = new MonteCarloResponse();
        response.setPercentiles(new ArrayList<>());

        for (RiskReturn riskReturn : request.getRiskReturns()) {
            NormalDistribution distribution = new NormalDistribution(riskReturn.getAverageReturn().doubleValue(), riskReturn.getRisk().doubleValue()); //Can pass random, here. Not sure the use
            List<BigDecimal> projectedValues = projectValues(request, distribution);
            response.getPercentiles().add(calcPercentiles(projectedValues));
        }

        return response;

    }

    private MonteCarloRequest validateAndCorrectRequest(MonteCarloRequest request) {
        BigDecimal bigDecimal100 = BigDecimal.valueOf(100);

        for (RiskReturn riskReturn : request.getRiskReturns()) {
            riskReturn.setAverageReturn(riskReturn.getAverageReturn().divide(bigDecimal100, 10, RoundingMode.HALF_EVEN));
            riskReturn.setRisk(riskReturn.getRisk().divide(bigDecimal100, 10, RoundingMode.HALF_EVEN));
        }
        request.setInflationRate(request.getInflationRate().divide(bigDecimal100, 10, RoundingMode.HALF_EVEN));

        if (request.getSimulationCount() == null || request.getSimulationCount() <= 0) {
            request.setSimulationCount(10000);
        }

        return request;
    }


    private List<BigDecimal> projectValues(MonteCarloRequest request, NormalDistribution distribution) {
        List<BigDecimal> simulatedValues = new ArrayList<>(request.getSimulationCount());
        for (int i = 0; i < request.getSimulationCount(); i++) {

            simulatedValues.add(yearlyReturn(request, distribution));
        }

        return simulatedValues;
    }

    /**
     * Not sure if the inflation formula is correct. Formula used: balance -= balance * inflationRate
     */
    private BigDecimal yearlyReturn(MonteCarloRequest request, NormalDistribution distribution) {

        BigDecimal balance = request.getInvestedAmountInitial();

        for (int i = 0; i < request.getInvestedYears(); i++) {
            balance = balance.multiply(BigDecimal.valueOf(1 + distribution.inverseCumulativeProbability(random.nextDouble())));
            balance = balance.subtract(balance.multiply(request.getInflationRate())); //Adjust for inflation
            //inflationFormula2: balance = balance * (100 / (100 + request.getInflationRate() ));
        }

        return balance;

    }


// Older version
//    private MonteCarloResponse calcResponse(List<Double> aggressiveValues, List<Double> conservativeValues) {
//
//        double[] aggressiveArray = aggressiveValues.stream()
//                .mapToDouble(Double::doubleValue)
//                .sorted()
//                .toArray();
//
//        double[] conservativeArray = conservativeValues.stream()
//                .mapToDouble(Double::doubleValue)
//                .sorted()
//                .toArray();
//
//        Percentile percentile90 = new Percentile(90);
//        Percentile percentile50 = new Percentile(50);
//        Percentile percentile10 = new Percentile(10);
//
//    }


    private Percentiles calcPercentiles(List<BigDecimal> list) {

        Collections.sort(list);

        BigDecimal percentile50 = list.get(list.size() / 2);
        if (list.size() % 2 == 0) {
            //(median + list.get(midList - 1)) / 2;
            percentile50 = percentile50.add(list.get((list.size() / 2) - 1));
            percentile50 = percentile50.divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_EVEN);
        }

        Percentiles percentiles = new Percentiles();
        percentiles.setPercentile50(percentile50);
        percentiles.setPercentile90(list.get(list.size() * 9 / 10));
        percentiles.setPercentile10(list.get(list.size() / 10));

        return percentiles;
    }


}
