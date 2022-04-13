package com.demo.coding.validator;

import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.WorkOrderType;

public class TestUtils {

    public static WorkOrderDto.WorkOrderDtoBuilder analysisWorkOrderBuilder() {
        return WorkOrderDto
                .builder()
                .type(WorkOrderType.ANALYSIS.name())
                .department("Test department")
                .startDate("2020-08-13")
                .endDate("2020-08-16")
                .currency("USD")
                .cost("123.12");
    }

    public static PartDto.PartDtoBuilder partDtoBuilder() {
        return PartDto
                .builder()
                .name("Part name")
                .count(1)
                .inventoryNumber("Some inventory number");
    }

}
