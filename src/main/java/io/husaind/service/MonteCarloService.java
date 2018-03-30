package io.husaind.service;

import io.husaind.dto.MonteCarloRequest;
import io.husaind.dto.MonteCarloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonteCarloService {


    @Autowired
    public MonteCarloService() {

    }


    public MonteCarloResponse simulate(MonteCarloRequest request) {

        return MonteCarloResponse.builder().build();
    }

}
