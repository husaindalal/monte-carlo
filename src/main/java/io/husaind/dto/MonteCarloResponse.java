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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonteCarloResponse{");
        sb.append("\n aggressive90Percentile=").append(aggressive90Percentile);
        sb.append(",\n aggressive10Percentile=").append(aggressive10Percentile);
        sb.append(",\n aggressiveMedian=").append(aggressiveMedian);
        sb.append(",\n conservative90Percentile=").append(conservative90Percentile);
        sb.append(",\n conservative10Percentile=").append(conservative10Percentile);
        sb.append(",\n conservativeMedian=").append(conservativeMedian);
        sb.append('}');
        return sb.toString();
    }
}
