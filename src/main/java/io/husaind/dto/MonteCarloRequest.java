package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloRequest implements Serializable {

    @NotNull
    private List<RiskReturn> riskReturns;

    @NotNull
    private BigDecimal investedAmountInitial;

    @NotNull
    private Integer investedYears;

    @NotNull
    private BigDecimal inflationRate;

    @NotNull
    private Integer simulationCount;

}
