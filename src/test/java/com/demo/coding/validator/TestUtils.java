package com.demo.coding.validator;

import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.Analysis;
import com.demo.coding.validator.domain.WorkOrder;
import com.demo.coding.validator.domain.WorkOrderPart;
import com.demo.coding.validator.domain.WorkOrderType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class TestUtils {

    public static final String DEPARTMENT = "department";
    public static final String START_DATE = "2020-08-13";
    public static final String END_DATE = "2020-08-16";
    public static final String USD = "USD";
    public static final String COST = "123.12";
    public static final String NAME = "name";
    public static final String INVENTORY_NUMBER = "inv#";
    public static final String PART_NAME = "Part name";
    public static final String PART_INVENTORY_NUMBER = "Some inventory number";



    public static WorkOrderDto.WorkOrderDtoBuilder analysisWorkOrderDtoBuilder() {
        return WorkOrderDto
                .builder()
                .type(WorkOrderType.ANALYSIS.name())
                .department(DEPARTMENT)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .currency(USD)
                .cost(COST);
    }

    public static PartDto.PartDtoBuilder partDtoBuilder() {
        return PartDto
                .builder()
                .name(PART_NAME)
                .count(1)
                .inventoryNumber(PART_INVENTORY_NUMBER);
    }

    public static WorkOrder analysisWorkOrder() {
        var workOrderParts = List.of(
                WorkOrderPart
                        .builder()
                        .name(PART_NAME)
                        .count(1)
                        .inventoryNumber(PART_INVENTORY_NUMBER)
                        .build());
        return new Analysis(
                DEPARTMENT,
                LocalDate.parse(START_DATE),
                LocalDate.parse(END_DATE),
                Currency.getInstance(USD),
                BigDecimal.valueOf(Double.parseDouble(COST)),
                workOrderParts
        );
    }

}
