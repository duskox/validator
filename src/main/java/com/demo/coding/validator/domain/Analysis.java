package com.demo.coding.validator.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class Analysis {
    String department;
    LocalDate startDate;
    LocalDate endDate;
    String currency;
    BigDecimal cost;
    List<Part> parts;
}
