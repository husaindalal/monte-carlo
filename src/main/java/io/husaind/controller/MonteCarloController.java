package io.husaind.controller;

import io.husaind.dto.MonteCarloRequest;
import io.husaind.dto.MonteCarloResponse;
import io.husaind.service.MonteCarloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/monte-carlo")

public class MonteCarloController {

    private MonteCarloService monteCarloService;

    @Autowired
    public MonteCarloController(MonteCarloService monteCarloService) {
        this.monteCarloService = monteCarloService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MonteCarloResponse simulate(@Valid @RequestBody MonteCarloRequest monteCarloRequest) {
        return monteCarloService.simulate(monteCarloRequest);
    }


}
