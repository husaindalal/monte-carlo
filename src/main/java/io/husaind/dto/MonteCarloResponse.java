package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloResponse implements Serializable {

    //TODO should be ideally BigDecimal
    private Double aggressive90Percentile;
    private Double aggressive10Percentile;
    private Double aggressiveMedian;

    private Double conservative90Percentile;
    private Double conservative10Percentile;
    private Double conservativeMedian;

}
