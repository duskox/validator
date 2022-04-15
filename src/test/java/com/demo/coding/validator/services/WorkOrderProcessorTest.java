package com.demo.coding.validator.services;

import com.demo.coding.validator.api.errors.InvalidWorkOrderException;
import com.demo.coding.validator.domain.*;
import com.demo.coding.validator.persistence.WorkOrderStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import static com.demo.coding.validator.TestUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WorkOrderProcessorTest {

    public static final String FACTORY_NAME = "factoryName";
    public static final String FACTORY_ORDER_NUMBER = "DA12345678";
    public static final String RESPONSIBLE_PERSON = "responsiblePerson";
    private WorkOrderProcessor workOrderProcessor;

    @Mock
    WorkOrderStore workOrderStore;

    @BeforeEach
    void setUp() {
        workOrderProcessor = new WorkOrderProcessor(workOrderStore);
    }

    @Test
    void givenValidDto_whenValidating_thenValidatePositiveResult() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderDtoBuilder()
                .parts(List.of(partDto))
                .build();

        var expected = analysisWorkOrder();

        var result = workOrderProcessor.validateWorkOrder(analysisDto);

        assertEquals(expected, result);
    }

    @Test
    void givenInvalidDepartment_whenValidating_thenValidateFaults() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderDtoBuilder()
                .parts(List.of(partDto))
                .department("")
                .build();
        var expected = Map.of("department", "Field should not be blank");

        var result = assertThrows(InvalidWorkOrderException.class,
                () -> workOrderProcessor.validateWorkOrder(analysisDto));

        assertTrue(result.getReason().equals("ANALYSIS_VALIDATION_FAILED"));
        assertTrue(result.getDetails().equals(expected));
    }

    @Test
    void givenAnalysis_whenValidating_thenValidateDomainObject() {
        var partDto = partDtoBuilder().build();
        var payload = analysisWorkOrderDtoBuilder()
                .parts(List.of(partDto))
                .build();

        var part = WorkOrderPart
                .builder()
                .name(partDto.getName())
                .inventoryNumber(partDto.getInventoryNumber())
                .count(partDto.getCount())
                .build();

        var expected = new Analysis(
                payload.getDepartment(),
                LocalDate.parse(payload.getStartDate()),
                LocalDate.parse(payload.getEndDate()),
                Currency.getInstance(payload.getCurrency()),
                BigDecimal.valueOf(Double.parseDouble(payload.getCost())),
                List.of(part));

        var actual = workOrderProcessor.validateWorkOrder(payload);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenReplacement_whenValidating_thenValidateDomainObject() {
        var partDto = partDtoBuilder().build();
        var payload = analysisWorkOrderDtoBuilder()
                .type(WorkOrderType.REPLACEMENT.name())
                .parts(List.of(partDto))
                .factoryName(FACTORY_NAME)
                .factoryOrderNumber(FACTORY_ORDER_NUMBER)
                .build();

        var part = WorkOrderPart
                .builder()
                .name(partDto.getName())
                .inventoryNumber(partDto.getInventoryNumber())
                .count(partDto.getCount())
                .build();

        var expected = new Replacement(
                payload.getDepartment(),
                LocalDate.parse(payload.getStartDate()),
                LocalDate.parse(payload.getEndDate()),
                Currency.getInstance(payload.getCurrency()),
                BigDecimal.valueOf(Double.parseDouble(payload.getCost())),
                List.of(part),
                FACTORY_NAME,
                FACTORY_ORDER_NUMBER);

        var actual = workOrderProcessor.validateWorkOrder(payload);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void givenRepair_whenValidating_thenValidateDomainObject() {
        var partDto = partDtoBuilder().build();
        var payload = analysisWorkOrderDtoBuilder()
                .type(WorkOrderType.REPAIR.name())
                .parts(List.of(partDto))
                .startDate(LocalDate.now().minusDays(5).toString())
                .endDate(LocalDate.now().minusDays(1).toString())
                .analysisDate(LocalDate.now().minusDays(4).toString())
                .testDate(LocalDate.now().minusDays(2).toString())
                .responsiblePerson(RESPONSIBLE_PERSON)
                .build();

        var part = WorkOrderPart
                .builder()
                .name(partDto.getName())
                .inventoryNumber(partDto.getInventoryNumber())
                .count(partDto.getCount())
                .build();

        var expected = new Repair(
                payload.getDepartment(),
                LocalDate.parse(payload.getStartDate()),
                LocalDate.parse(payload.getEndDate()),
                Currency.getInstance(payload.getCurrency()),
                BigDecimal.valueOf(Double.parseDouble(payload.getCost())),
                List.of(part),
                LocalDate.parse(payload.getAnalysisDate()),
                RESPONSIBLE_PERSON,
                LocalDate.parse(payload.getTestDate()));

        var actual = workOrderProcessor.validateWorkOrder(payload);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}