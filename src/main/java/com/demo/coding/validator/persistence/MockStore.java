package com.demo.coding.validator.persistence;

import com.demo.coding.validator.domain.Analysis;
import com.demo.coding.validator.domain.WorkOrder;
import com.demo.coding.validator.domain.WorkOrderPart;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class MockStore implements WorkOrderStore {

    @Override
    public void saveWorkOrder(WorkOrder workOrder) {

    }

    @Override
    public List<WorkOrder> getWorkOrders() {
        var part = WorkOrderPart
                .builder()
                .count(1)
                .inventoryNumber("invNum1")
                .name("partName1")
                .build();
        var workOrder = new Analysis("departmentOne",
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(1),
                Currency.getInstance("USD"),
                BigDecimal.valueOf(123.12),
                List.of(part));

        return List.of(workOrder);
    }
}
