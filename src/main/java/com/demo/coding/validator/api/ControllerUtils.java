package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.*;

import java.util.stream.Collectors;

public class ControllerUtils {

    public static WorkOrderDto toDto(WorkOrder workOrder) {
        WorkOrderDto result = null;

        var parts = workOrder
                .getWorkOrderParts()
                .stream()
                .map(ControllerUtils::toDto)
                .collect(Collectors.toList());

        if (workOrder instanceof Analysis) {
            result = WorkOrderDto
                    .builder()
                    .type(WorkOrderType.ANALYSIS.name())
                    .department(workOrder.getDepartment())
                    .startDate(workOrder.getStartDate().toString())
                    .endDate(workOrder.getEndDate().toString())
                    .currency(workOrder.getCurrency().getCurrencyCode())
                    .cost(workOrder.getCost().toString())
                    .parts(parts)
                    .build();
        }

        if (workOrder instanceof Repair) {
            result = ControllerUtils.toDto((Repair) workOrder);
        }

        if (workOrder instanceof Replacement) {
            result = ControllerUtils.toDto((Replacement) workOrder);
        }

        return result;
    }

    public static PartDto toDto(WorkOrderPart workOrderPart) {
        return PartDto
                .builder()
                .count(workOrderPart.getCount())
                .inventoryNumber(workOrderPart.getInventoryNumber())
                .name(workOrderPart.getName())
                .build();
    }

    public static WorkOrderDto toDto(Repair repair) {
        var parts = repair
                .getWorkOrderParts()
                .stream()
                .map(ControllerUtils::toDto)
                .collect(Collectors.toList());

        return WorkOrderDto
                .builder()
                .type(WorkOrderType.REPAIR.name())
                .department(repair.getDepartment())
                .startDate(repair.getStartDate().toString())
                .endDate(repair.getEndDate().toString())
                .analysisDate(repair.getAnalysisDate().toString())
                .testDate(repair.getTestDate().toString())
                .responsiblePerson(repair.getResponsiblePerson())
                .currency(repair.getCurrency().getCurrencyCode())
                .cost(repair.getCost().toString())
                .parts(parts)
                .build();
    }

    public static WorkOrderDto toDto(Replacement replacement) {
        var parts = replacement
                .getWorkOrderParts()
                .stream()
                .map(ControllerUtils::toDto)
                .collect(Collectors.toList());

        return WorkOrderDto
                .builder()
                .type(WorkOrderType.REPLACEMENT.name())
                .department(replacement.getDepartment())
                .startDate(replacement.getStartDate().toString())
                .endDate(replacement.getEndDate().toString())
                .currency(replacement.getCurrency().getCurrencyCode())
                .cost(replacement.getCost().toString())
                .factoryName(replacement.getFactoryName())
                .factoryOrderNumber(replacement.getFactoryOrderNumber())
                .parts(parts)
                .build();
    }
}
