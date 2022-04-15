package com.demo.coding.validator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@AllArgsConstructor
@Getter
public abstract class WorkOrder {
    String department;
    LocalDate startDate;
    LocalDate endDate;
    Currency currency;
    BigDecimal cost;
    List<WorkOrderPart> workOrderParts;
}
