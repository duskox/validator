package com.demo.coding.validator.persistence;

import com.demo.coding.validator.domain.WorkOrder;

import java.util.List;

public interface WorkOrderStore {
    void saveWorkOrder(WorkOrder workOrder);
    List<WorkOrder> getWorkOrders();
}
