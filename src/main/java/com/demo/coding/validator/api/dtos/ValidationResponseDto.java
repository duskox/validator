package com.demo.coding.validator.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonSerialize
public class ValidationResponseDto {
    @JsonProperty("valid")
    boolean valid;
}
