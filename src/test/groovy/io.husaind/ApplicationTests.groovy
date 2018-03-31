package io.husaind

import io.husaind.dto.MonteCarloRequest
import io.husaind.dto.MonteCarloResponse
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
        MonteCarloRequest request = new MonteCarloRequest()
        request.aggressiveReturn = 9.4324d
        request.aggressiveRisk = 15.675d
        request.conservativeReturn = 7.2d //6.189d
        request.conservativeRisk = 7.4d //6.3438d
        request.investedAmountInitial = 100000d
        request.investedYears = 20d
        request.inflationRate = 3.5d
        request.simulationCount = 10000d


        MonteCarloResponse response = monteCarloService.simulate(request)
        then: "check response"
        println response
        response.aggressiveMedian != 0
        response.conservativeMedian != 0

    }

    /*
        private Double aggressiveReturn;// = new BigDecimal("9.4324"); //Mean / Average

    private Double aggressiveRisk;// = new BigDecimal("15.675"); //Standard Deviation

    private Double conservativeReturn;// = new BigDecimal("6.189");

    private Double conservativeRisk;// = new BigDecimal("6.3438");

    private Double investedAmountInitial;// = new BigDecimal("10000"); //10000

    private Double investedAmountYearly;// = new BigDecimal("0"); //10000

    private Double investedYears;// = new BigDecimal("20");

    private Double inflationRate;// = new BigDecimal("3.5");

    private Double simulationCount;// = new BigDecimal("10000");

     */

}
