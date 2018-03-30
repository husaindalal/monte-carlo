package io.husaind

import io.husaind.dto.MonteCarloRequest
import io.husaind.dto.MonteCarloResponse
import io.husaind.service.MonteCarloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = Application.class)

class ApplicationTests extends Specification {

    @Autowired
    MonteCarloService monteCarloService

    def "simulate results"() {
        when: "create params and simulate"
        MonteCarloRequest request = MonteCarloRequest.builder()
                                    .build()
        MonteCarloResponse response = monteCarloService.simulate(request)
        then: "check response"
        response.aggressiveMedian != 0
        response.conservativeMedian != 0

    }

}
