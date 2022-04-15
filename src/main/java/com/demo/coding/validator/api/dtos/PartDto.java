package com.demo.coding.validator.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDto {
    @JsonProperty("inventory_number")
    String inventoryNumber;
    @JsonProperty("name")
    String name;
    @JsonProperty("count")
    int count;
}
