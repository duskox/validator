package com.demo.coding.validator.domain;

import com.demo.coding.validator.api.dtos.PartDto;

public class FieldUtils {

    public static Part toDomainObject(PartDto partDto) {
        return Part.builder()
                .name(partDto.getName())
                .count(partDto.getCount())
                .inventoryNumber(partDto.getInventoryNumber())
                .build();
    }
}
