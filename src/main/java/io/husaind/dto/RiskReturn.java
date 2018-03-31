package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class RiskReturn {

    @NotNull
    private BigDecimal averageReturn; //Mean / Average

    @NotNull
    private BigDecimal risk; //Standard Deviation

}
