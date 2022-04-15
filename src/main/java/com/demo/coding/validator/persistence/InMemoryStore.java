package com.demo.coding.validator.persistence;

import com.demo.coding.validator.domain.*;
import com.demo.coding.validator.persistence.dbo.PartDbo;
import com.demo.coding.validator.persistence.dbo.WorkOrderDbo;
import com.demo.coding.validator.persistence.utils.UuidProvider;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InMemoryStore implements WorkOrderStore {

    private final WorkOrdersRepository workOrdersRepository;
    private final PartsRepository partsRepository;
    private final UuidProvider uuidProvider;

    public InMemoryStore(WorkOrdersRepository workOrdersRepository,
                         PartsRepository partsRepository,
                         UuidProvider uuidProvider) {
        this.workOrdersRepository = workOrdersRepository;
        this.partsRepository = partsRepository;
        this.uuidProvider = uuidProvider;
    }

    @Override
    public void saveWorkOrder(WorkOrder workOrder) {
        var internalWorkOrderId = uuidProvider.create();
        workOrdersRepository.save(this.toDbo(workOrder, internalWorkOrderId));
        workOrder
                .getWorkOrderParts()
                .stream()
                .map(workOrderPart -> this.toDbo(workOrderPart, internalWorkOrderId))
                .map(partsRepository::save);
    }

    @Override
    public List<WorkOrder> getWorkOrders() {
        return workOrdersRepository
                .findAll()
                .stream()
                .map(this::assembleWorkOrder)
                .collect(Collectors.toList());
    }

    private WorkOrder assembleWorkOrder(WorkOrderDbo workOrderDbo) {
        var workOrderParts = getPartsForWorkOrder(workOrderDbo)
                .stream()
                .map(this::dboToPart)
                .collect(Collectors.toList());
        return dboToWorkOrder(workOrderDbo, workOrderParts);
    }

    private List<PartDbo> getPartsForWorkOrder(WorkOrderDbo workOrderDbo) {
        return partsRepository.findAllByWorkOrderId(workOrderDbo.getId());
    }

    private WorkOrder dboToWorkOrder(WorkOrderDbo workOrderDbo, List<WorkOrderPart> workOrderParts) {
        var type = WorkOrderType.valueOf(workOrderDbo.getType());
        WorkOrder workOrder = null;
        switch (type) {
            case ANALYSIS -> workOrder = assembleAnalysis(workOrderDbo, workOrderParts);
            case REPAIR -> workOrder = assembleRepair(workOrderDbo, workOrderParts);
            case REPLACEMENT -> workOrder = assembleReplacement(workOrderDbo, workOrderParts);
        }
        return workOrder;
    }

    private WorkOrder assembleAnalysis(WorkOrderDbo workOrderDbo, List<WorkOrderPart> workOrderParts) {
        return new Analysis(
                workOrderDbo.getDepartment(),
                workOrderDbo.getStartDate(),
                workOrderDbo.getEndDate(),
                Currency.getInstance(workOrderDbo.getCurrency()),
                workOrderDbo.getCost(),
                workOrderParts);
    }

    private WorkOrder assembleRepair(WorkOrderDbo workOrderDbo, List<WorkOrderPart> workOrderParts) {
        return new Repair(
                workOrderDbo.getDepartment(),
                workOrderDbo.getStartDate(),
                workOrderDbo.getEndDate(),
                Currency.getInstance(workOrderDbo.getCurrency()),
                workOrderDbo.getCost(),
                workOrderParts,
                workOrderDbo.getAnalysisDate(),
                workOrderDbo.getResponsiblePerson(),
                workOrderDbo.getTestDate());
    }

    private WorkOrder assembleReplacement(WorkOrderDbo workOrderDbo, List<WorkOrderPart> workOrderParts) {
        return new Replacement(
                workOrderDbo.getDepartment(),
                workOrderDbo.getStartDate(),
                workOrderDbo.getEndDate(),
                Currency.getInstance(workOrderDbo.getCurrency()),
                workOrderDbo.getCost(),
                workOrderParts,
                workOrderDbo.getFactoryName(),
                workOrderDbo.getFactoryOrderNumber());
    }

    private WorkOrderPart dboToPart(PartDbo partDbo) {
        return WorkOrderPart
                .builder()
                .count(partDbo.getCount())
                .inventoryNumber(partDbo.getInventoryNumber())
                .name(partDbo.getName())
                .build();
    }

    private PartDbo toDbo(WorkOrderPart workOrderPart, UUID uuid) {
        return new PartDbo(
                uuidProvider.create().toString(),
                uuid.toString(),
                workOrderPart.getName(),
                workOrderPart.getCount(),
                workOrderPart.getInventoryNumber());
    }

    private WorkOrderDbo toDbo(WorkOrder workOrder, UUID uuid) {
        WorkOrderDbo result = null;
        if (workOrder instanceof Analysis) {
            result = new WorkOrderDbo(
                    uuid.toString(),
                    WorkOrderType.ANALYSIS.name(),
                    workOrder.getDepartment(),
                    workOrder.getStartDate(),
                    workOrder.getEndDate(),
                    null,
                    null,
                    workOrder.getCurrency().getCurrencyCode(),
                    workOrder.getCost(),
                    null,
                    null,
                    null);
        }

        if (workOrder instanceof Repair) {
            result = this.toDbo((Repair) workOrder, uuid);
        }

        if (workOrder instanceof Replacement) {
            result = this.toDbo((Replacement) workOrder, uuid);
        }

        return result;
    }

    private WorkOrderDbo toDbo(Repair repair, UUID uuid) {
        return new WorkOrderDbo(
                uuid.toString(),
                WorkOrderType.ANALYSIS.name(),
                repair.getDepartment(),
                repair.getStartDate(),
                repair.getEndDate(),
                repair.getAnalysisDate(),
                repair.getTestDate(),
                repair.getCurrency().getCurrencyCode(),
                repair.getCost(),
                repair.getResponsiblePerson(),
                null,
                null);
    }

    private WorkOrderDbo toDbo(Replacement replacement, UUID uuid) {
        return new WorkOrderDbo(
                uuid.toString(),
                WorkOrderType.ANALYSIS.name(),
                replacement.getDepartment(),
                replacement.getStartDate(),
                replacement.getEndDate(),
                null,
                null,
                replacement.getCurrency().getCurrencyCode(),
                replacement.getCost(),
                null,
                replacement.getFactoryName(),
                replacement.getFactoryOrderNumber());
    }
}
