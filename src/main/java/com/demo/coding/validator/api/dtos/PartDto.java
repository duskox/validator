package com.demo.coding.validator.api.dtos;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartDto {
    String inventoryNumber;
    String name;
    int count;
}
