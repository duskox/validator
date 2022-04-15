package com.demo.coding.validator.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Value
public class Repair extends WorkOrder {
    LocalDate analysisDate;
    String responsiblePerson;
    LocalDate testDate;

    public Repair(String department,
                  LocalDate startDate,
                  LocalDate endDate,
                  Currency currency,
                  BigDecimal cost,
                  List<WorkOrderPart> workOrderParts,
                  LocalDate analysisDate,
                  String responsiblePerson,
                  LocalDate testDate) {
        super(department, startDate, endDate, currency, cost, workOrderParts);
        this.analysisDate = analysisDate;
        this.responsiblePerson = responsiblePerson;
        this.testDate = testDate;
    }
}
