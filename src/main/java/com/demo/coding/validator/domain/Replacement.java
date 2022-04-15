package com.demo.coding.validator.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Value
public class Replacement extends WorkOrder {
    String factoryName;
    String factoryOrderNumber;

    public Replacement(String department,
                       LocalDate startDate,
                       LocalDate endDate,
                       Currency currency,
                       BigDecimal cost,
                       List<WorkOrderPart> workOrderParts,
                       String factoryName,
                       String factoryOrderNumber) {
        super(department, startDate, endDate, currency, cost, workOrderParts);
        this.factoryName = factoryName;
        this.factoryOrderNumber = factoryOrderNumber;
    }
}
