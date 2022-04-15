package com.demo.coding.validator.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Value
public class Analysis extends WorkOrder {
    public Analysis(String department,
                    LocalDate startDate,
                    LocalDate endDate,
                    Currency currency,
                    BigDecimal cost,
                    List<WorkOrderPart> workOrderParts) {
        super(department, startDate, endDate, currency, cost, workOrderParts);
    }
}
