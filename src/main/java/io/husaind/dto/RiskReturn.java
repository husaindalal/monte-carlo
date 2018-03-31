package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class RiskReturn {

    private BigDecimal averageReturn; //Mean / Average

    private BigDecimal risk; //Standard Deviation

}
