package com.demo.coding.validator.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@AllArgsConstructor
public abstract class WorkOrder {
    String department;
    LocalDate startDate;
    LocalDate endDate;
    Currency currency;
    BigDecimal cost;
    List<Part> parts;
}
