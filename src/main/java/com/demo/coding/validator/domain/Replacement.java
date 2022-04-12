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
                       List<Part> parts,
                       String factoryName,
                       String factoryOrderNumber) {
        super(department, startDate, endDate, currency, cost, parts);
        this.factoryName = factoryName;
        this.factoryOrderNumber = factoryOrderNumber;
    }
}
