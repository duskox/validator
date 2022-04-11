package com.demo.coding.validator.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Repair extends Analysis {
    LocalDate analysisDate;
    String responsiblePerson;
    LocalDate testDate;
}
