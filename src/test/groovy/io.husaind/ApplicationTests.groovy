package io.husaind

import io.husaind.dto.MonteCarloRequest
import io.husaind.dto.MonteCarloResponse
import io.husaind.dto.RiskReturn
import io.husaind.service.MonteCarloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = Application.class)

class ApplicationTests extends Specification {

    @Autowired
    MonteCarloService monteCarloService

    def "simulate results"() {
        when: "create params and simulate"

        RiskReturn aggressive = new RiskReturn(averageReturn: 9.4324d, risk: 15.675d)
        RiskReturn conservative = new RiskReturn(averageReturn: 6.189d, risk: 6.3438d)

        MonteCarloRequest request = new MonteCarloRequest()
        request.riskReturns = [aggressive, conservative]
        request.investedAmountInitial = 100000d
        request.investedYears = 20d
        request.inflationRate = 3.5d
        request.simulationCount = 10000d

        MonteCarloResponse response = monteCarloService.simulate(request)


        then: "check response"
        println response

        response.percentiles.size() == 2

        response.percentiles[0].percentile50 > 100000
        response.percentiles[1].percentile50 > 100000

    }

}
