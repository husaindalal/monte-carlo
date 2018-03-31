package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloRequest implements Serializable {

    private List<RiskReturn> riskReturns;

    private BigDecimal investedAmountInitial;

    private Integer investedYears;

    private BigDecimal inflationRate;

    private Integer simulationCount;

}
