package com.demo.coding.validator.services;

import com.demo.coding.validator.api.errors.InvalidWorkOrderException;
import com.demo.coding.validator.persistence.WorkOrderStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static com.demo.coding.validator.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WorkOrderProcessorTest {

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

}