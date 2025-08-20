package io.husaind.service

import io.husaind.dto.MonteCarloRequest
import io.husaind.dto.RiskReturn
import io.husaind.dto.MonteCarloRequest
import io.husaind.dto.RiskReturn
import org.apache.commons.math3.distribution.NormalDistribution
import spock.lang.Specification
import spock.lang.Unroll

class MonteCarloServiceTest extends Specification {

    def monteCarloService = new MonteCarloService()

    @Unroll
    def "test validateAndCorrectRequest with #testName"() {
        given:
        def request = new MonteCarloRequest(
                riskReturns: [
                        new RiskReturn(averageReturn: 10, risk: 5)
                ],
                investedAmountInitial: 1000,
                investedYears: 10,
                inflationRate: 2,
                simulationCount: simulationCount
        )

        when: "The service is called with a request"
        def result = monteCarloService.invokeMethod("validateAndCorrectRequest", request)

        then: "The request is validated and corrected"
        result.riskReturns[0].averageReturn.round(2) == 0.10
        result.riskReturns[0].risk.round(2) == 0.05
        result.inflationRate.round(2) == 0.02
        result.simulationCount == expectedSimulationCount

        where:
        testName                  | simulationCount | expectedSimulationCount
        "valid simulation count"  | 1000            | 1000
        "null simulation count"   | null            | 10000
        "zero simulation count"   | 0               | 10000
    }

    def "test calcPercentiles"() {
        given:
        def values = (1..100).collect { new BigDecimal(it) }

        when:
        def result = monteCarloService.invokeMethod("calcPercentiles", values)

        then:
        result.percentile10 == 11
        result.percentile50 == 50.5
        result.percentile90 == 91
    }

    def "test simulate"() {
        given:
        def request = new MonteCarloRequest(
                riskReturns: [
                        new RiskReturn(averageReturn: 10, risk: 5)
                ],
                investedAmountInitial: 1000,
                investedYears: 1,
                inflationRate: 2,
                simulationCount: 100
        )

        def distribution = Mock(NormalDistribution)
        NormalDistribution.metaClass.constructor = { double mean, double risk -> distribution }
        distribution.inverseCumulativeProbability(_) >>> 0.1

        when:
        def result = monteCarloService.simulate(request)

        then:
        result.percentiles.size() == 1
        result.percentiles[0].percentile10.round(2) == 1078.00
        result.percentiles[0].percentile50.round(2) == 1078.00
        result.percentiles[0].percentile90.round(2) == 1078.00
    }
}
