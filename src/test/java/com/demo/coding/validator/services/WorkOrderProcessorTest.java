package com.demo.coding.validator.services;

import com.demo.coding.validator.api.errors.InvalidWorkOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static com.demo.coding.validator.TestUtils.analysisWorkOrderBuilder;
import static com.demo.coding.validator.TestUtils.partDtoBuilder;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WorkOrderProcessorTest {

    private WorkOrderProcessor workOrderProcessor;

    @BeforeEach
    void setUp() {
        workOrderProcessor = new WorkOrderProcessor();
    }

    @Test
    void givenValidDto_whenValidating_thenValidatePositiveResult() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();

        var result = workOrderProcessor.validateWorkOrder(analysisDto);

        assertTrue(result);
    }

    @Test
    void givenInvalidDepartment_whenValidating_thenValidateFaults() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
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