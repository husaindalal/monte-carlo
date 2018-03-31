package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloRequest implements Serializable {

    private Double aggressiveReturn;// = new BigDecimal("9.4324"); //Mean / Average

    private Double aggressiveRisk;// = new BigDecimal("15.675"); //Standard Deviation

    private Double conservativeReturn;// = new BigDecimal("6.189");

    private Double conservativeRisk;// = new BigDecimal("6.3438");

    private Double investedAmountInitial;// = new BigDecimal("10000"); //10000

    //private Double investedAmountYearly;// = new BigDecimal("0"); //10000

    private Double investedYears;// = new BigDecimal("20");

    private Double inflationRate;// = new BigDecimal("3.5");

    private Double simulationCount;// = new BigDecimal("10000");

}
