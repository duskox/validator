package com.demo.coding.validator.domain;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationTest {

    @Test
    void givenValidDepartment_whenValidating_thenMaintainValidStatus() {
        // given
        var workOrderDto = WorkOrderDto
                .builder()
                .department("some department")
                .build();
        var fieldValidation = new FieldValidation();

        // when
        var result = fieldValidation.validateDepartment(workOrderDto);

        // then
        assertEquals(workOrderDto.getDepartment(), result);
        assertTrue(fieldValidation.isValid());
    }

    @Test
    void givenInvalidDepartment_whenValidating_thenUpdateFailedStatus() {
        // given
        var workOrderDto = WorkOrderDto
                .builder()
                .department("")
                .build();
        var expected = Map.of(
                "department",
                "Field should not be blank");
        var fieldValidation = new FieldValidation();

        // when
        var result = fieldValidation.validateDepartment(workOrderDto);

        // then
        assertEquals(workOrderDto.getDepartment(), result);
        assertFalse(fieldValidation.isValid());
        assertEquals(expected, fieldValidation.getFaultsMap());
    }
}