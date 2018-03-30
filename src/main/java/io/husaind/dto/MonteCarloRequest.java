package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloRequest implements Serializable {

    private BigDecimal aggressiveReturn = new BigDecimal("9.4324"); //Mean / Average

    private BigDecimal aggressiveRisk = new BigDecimal("15.675"); //Standard Deviation

    private BigDecimal conservativeReturn = new BigDecimal("6.189");

    private BigDecimal conservativeRisk = new BigDecimal("6.3438");

    private BigDecimal investedAmount = new BigDecimal("10000"); //10000

    private BigDecimal investedYears = new BigDecimal("20");

    private BigDecimal inflationRate = new BigDecimal("3.5");

    private BigDecimal simulationCount = new BigDecimal("10000");

}
