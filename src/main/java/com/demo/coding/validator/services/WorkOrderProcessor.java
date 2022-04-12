package com.demo.coding.validator.services;

import com.demo.coding.validator.api.dtos.ValidationResponseDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.api.errors.InvalidWorkOrderException;
import com.demo.coding.validator.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderProcessor {

    public ValidationResponseDto validateWorkOrder(WorkOrderDto workOrderDto) {

        var workOrderType = validateWorkOrderType(workOrderDto);

        WorkOrder workOrder;
        switch (workOrderType) {
            case ANALYSIS -> workOrder = mapToAnalysisWorkOrder(workOrderDto);
            case REPAIR -> workOrder = mapToRepairWorkOrder(workOrderDto);
            case REPLACEMENT -> workOrder = mapToReplacementWorkOrder(workOrderDto);
        }


        return ValidationResponseDto.builder().build();
    }

    private WorkOrderType validateWorkOrderType(WorkOrderDto workOrderDto) {
        try {
            return WorkOrderType.valueOf(workOrderDto.getType());
        } catch (Exception e) {
            throw new InvalidWorkOrderException("INVALID_WORK_ORDER_TYPE", null);
        }
    }

    private Analysis mapToAnalysisWorkOrder(WorkOrderDto workOrderDto) {
        var fieldValidation = new FieldValidation();

        var department = fieldValidation.validateDepartment(workOrderDto);
        var startDate = fieldValidation.validateStartDate(workOrderDto);
        var endDate = fieldValidation.validateEndDate(workOrderDto);
        var currency = fieldValidation.validateCurrency(workOrderDto);
        var cost = fieldValidation.validateCost(workOrderDto);
        List<Part> parts = workOrderDto
                .getParts()
                .stream()
                .map(FieldUtils::toDomainObject)
                .collect(Collectors.toList());

        if (!fieldValidation.isValid()) {
            throw new InvalidWorkOrderException("ANALYSIS_VALIDATION_FAILED", fieldValidation.getFaultsMap());
        }

        return new Analysis(department,
                startDate,
                endDate,
                currency,
                cost,
                parts);
    }

    private Repair mapToRepairWorkOrder(WorkOrderDto workOrderDto) {
        var fieldValidation = new FieldValidation();

        var department = fieldValidation.validateDepartment(workOrderDto);
        var startDate = fieldValidation.validateStartDate(workOrderDto);
        var endDate = fieldValidation.validateEndDate(workOrderDto);
        var currency = fieldValidation.validateCurrency(workOrderDto);
        var cost = fieldValidation.validateCost(workOrderDto);
        var parts = fieldValidation.validateAndExtractParts(workOrderDto);
        var analysisDate = fieldValidation.validateAnalysisDate(workOrderDto);
        var responsiblePerson = fieldValidation.validateResponsiblePerson(workOrderDto);
        var testDate = fieldValidation.validateTestDate(workOrderDto);

        if (!fieldValidation.isValid()) {
            throw new InvalidWorkOrderException("REPAIR_VALIDATION_FAILED", fieldValidation.getFaultsMap());
        }

        return new Repair(department,
                startDate,
                endDate,
                currency,
                cost,
                parts,
                analysisDate,
                responsiblePerson,
                testDate);
    }

    private Replacement mapToReplacementWorkOrder(WorkOrderDto workOrderDto) {
        var fieldValidation = new FieldValidation();

        var department = fieldValidation.validateDepartment(workOrderDto);
        var startDate = fieldValidation.validateStartDate(workOrderDto);
        var endDate = fieldValidation.validateEndDate(workOrderDto);
        var currency = fieldValidation.validateCurrency(workOrderDto);
        var cost = fieldValidation.validateCost(workOrderDto);
        var parts = fieldValidation.validateAndExtractParts(workOrderDto);
        fieldValidation.validatePartsInventoryNumber(parts);
        var factoryName = fieldValidation.validateFactoryName(workOrderDto);
        var factoryOrderNumber = fieldValidation.validateFactoryOrderNumber(workOrderDto);

        if (!fieldValidation.isValid()) {
            throw new InvalidWorkOrderException("REPLACEMENT_VALIDATION_FAILED", fieldValidation.getFaultsMap());
        }

        return new Replacement(department,
                startDate,
                endDate,
                currency,
                cost,
                parts,
                factoryName,
                factoryOrderNumber);
    }


}
