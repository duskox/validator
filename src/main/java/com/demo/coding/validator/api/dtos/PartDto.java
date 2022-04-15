package com.demo.coding.validator.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
