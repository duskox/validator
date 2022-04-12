package com.demo.coding.validator.api.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PartDto {
    String inventoryNumber;
    String name;
    int count;
}
