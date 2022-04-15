package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ControllerUtilsTest {

    public static final String DEPARTMENT = "department";
    public static final LocalDate START_DATE = LocalDate.now().minusDays(5);
    public static final LocalDate END_DATE = LocalDate.now().minusDays(2);
    public static final LocalDate ANALYSIS_DATE = LocalDate.now().minusDays(4);
    public static final LocalDate TEST_DATE = LocalDate.now().minusDays(3);
    public static final String USD = "USD";
    public static final String PART_NAME = "partName";
    public static final String INVENTORY_NUMBER_1 = "inv1";
    public static final String INVENTORY_NUMBER_2 = "inv2";
    public static final String RESPONSIBLE = "Responsible person";
    public static final String FACTORY_NAME = "factoryName";
    public static final String FACTORY_ORDER_NUMBER = "factoryOrderNumber";

    @Test
    void givenAnalysis_whenConvertingToDto_thenValidateExpectedDto() {
        var part01 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_1)
                .count(2)
                .build();

        var part02 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_2)
                .count(1)
                .build();

        var analysis = new Analysis(DEPARTMENT,
                START_DATE,
                END_DATE,
                Currency.getInstance(USD),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02));

        var expectedParts = List.of(
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_1)
                        .count(2)
                        .build(),
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_2)
                        .count(1)
                        .build());
        var expected = WorkOrderDto
                .builder()
                .type(WorkOrderType.ANALYSIS.name())
                .department(DEPARTMENT)
                .startDate(START_DATE.toString())
                .endDate(END_DATE.toString())
                .currency(USD)
                .cost("123.12")
                .parts(expectedParts)
                .build();

        var actual = ControllerUtils.toDto(analysis);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenRepair_whenConvertingToDto_thenValidateExpectedDto() {
        var part01 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_1)
                .count(2)
                .build();

        var part02 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_2)
                .count(1)
                .build();

        var repair = new Repair(DEPARTMENT,
                START_DATE,
                END_DATE,
                Currency.getInstance(USD),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02),
                ANALYSIS_DATE,
                RESPONSIBLE,
                TEST_DATE);

        var expectedParts = List.of(
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_1)
                        .count(2)
                        .build(),
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_2)
                        .count(1)
                        .build());
        var expected = WorkOrderDto
                .builder()
                .type(WorkOrderType.REPAIR.name())
                .department(DEPARTMENT)
                .startDate(START_DATE.toString())
                .endDate(END_DATE.toString())
                .currency(USD)
                .cost("123.12")
                .parts(expectedParts)
                .testDate(TEST_DATE.toString())
                .analysisDate(ANALYSIS_DATE.toString())
                .responsiblePerson(RESPONSIBLE)
                .build();

        var actual = ControllerUtils.toDto(repair);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenReplacement_whenConvertingToDto_thenValidateExpectedDto() {
        var part01 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_1)
                .count(2)
                .build();

        var part02 = WorkOrderPart
                .builder()
                .name(PART_NAME)
                .inventoryNumber(INVENTORY_NUMBER_2)
                .count(1)
                .build();

        var replacement = new Replacement(DEPARTMENT,
                START_DATE,
                END_DATE,
                Currency.getInstance(USD),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02),
                FACTORY_NAME,
                FACTORY_ORDER_NUMBER);

        var expectedParts = List.of(
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_1)
                        .count(2)
                        .build(),
                PartDto
                        .builder()
                        .name(PART_NAME)
                        .inventoryNumber(INVENTORY_NUMBER_2)
                        .count(1)
                        .build());
        var expected = WorkOrderDto
                .builder()
                .type(WorkOrderType.REPLACEMENT.name())
                .department(DEPARTMENT)
                .startDate(START_DATE.toString())
                .endDate(END_DATE.toString())
                .currency(USD)
                .cost("123.12")
                .parts(expectedParts)
                .factoryOrderNumber(FACTORY_ORDER_NUMBER)
                .factoryName(FACTORY_NAME)
                .build();

        var actual = ControllerUtils.toDto(replacement);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

}