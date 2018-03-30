package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloResponse implements Serializable {

    private BigDecimal aggressive90Percentile;
    private BigDecimal aggressive10Percentile;
    private BigDecimal aggressiveMedian;

    private BigDecimal conservative90Percentile;
    private BigDecimal conservative10Percentile;
    private BigDecimal conservativeMedian;

}
