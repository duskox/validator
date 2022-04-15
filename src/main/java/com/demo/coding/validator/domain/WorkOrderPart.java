package com.demo.coding.validator.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkOrderPart {
    String inventoryNumber;
    String name;
    int count;
}
