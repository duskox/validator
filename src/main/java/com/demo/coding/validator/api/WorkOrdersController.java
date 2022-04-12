package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.ValidationResponseDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.WorkOrder;
import com.demo.coding.validator.services.WorkOrderProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/work-orders")
public class WorkOrdersController {

    private final WorkOrderProcessor workOrderProcessor;

    public WorkOrdersController(WorkOrderProcessor workOrderProcessor) {
        this.workOrderProcessor = workOrderProcessor;
    }

    @PostMapping()
    public Mono<ValidationResponseDto> validateWorkOrder(@RequestBody WorkOrderDto workOrderDto) {
        return Mono.just(workOrderProcessor.validateWorkOrder(workOrderDto));
    }
}
