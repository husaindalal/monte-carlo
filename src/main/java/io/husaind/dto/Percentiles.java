package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@NoArgsConstructor
//@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

public class Percentiles {

    private BigDecimal percentile90;
    private BigDecimal percentile50;
    private BigDecimal percentile10;


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.00");
        final StringBuilder sb = new StringBuilder("Percentiles{");
        sb.append("\n percentile90=").append(df.format(percentile90));
        sb.append(",\n percentile50=").append(df.format(percentile50));
        sb.append(",\n percentile10=").append(df.format(percentile10));
        sb.append("}\n");
        return sb.toString();
    }
}
