package io.husaind.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
//@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonteCarloResponse implements Serializable {

    private List<Percentiles> percentiles;


}
