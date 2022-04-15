package com.demo.coding.validator.domain;

import com.demo.coding.validator.api.dtos.PartDto;

public class FieldUtils {

    public static WorkOrderPart toDomainObject(PartDto partDto) {
        return WorkOrderPart.builder()
                .name(partDto.getName())
                .count(partDto.getCount())
                .inventoryNumber(partDto.getInventoryNumber())
                .build();
    }
}
