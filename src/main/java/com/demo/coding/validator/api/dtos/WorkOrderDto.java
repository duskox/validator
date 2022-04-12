package com.demo.coding.validator.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class WorkOrderDto {
    // Common & Analysis part
    @JsonProperty("type")
    String type;

    @JsonProperty("department")
    String department;
    @JsonProperty("start_date")
    String startDate;
    @JsonProperty("end_date")
    String endDate;
    @JsonProperty("currency")
    String currency;
    @JsonProperty("cost")
    String cost;
    @JsonProperty("parts")
    List<PartDto> parts;

    // Repair part
    @JsonProperty("analysis_date")
    String analysisDate;
    @JsonProperty("test_date")
    String testDate;
    @JsonProperty("responsible_person")
    String responsiblePerson;

    // Replacement part
    @JsonProperty("factory_name")
    String factoryName;
    @JsonProperty("factory_order_number")
    String factoryOrderNumber;
}
