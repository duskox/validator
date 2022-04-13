package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.services.WorkOrderProcessor;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity> validateWorkOrder(@RequestBody WorkOrderDto workOrderDto) {
        if (workOrderProcessor.validateWorkOrder(workOrderDto)) {
            return Mono.just(ResponseEntity.ok().build());
        }
        return Mono.just(ResponseEntity.badRequest().build());
    }
}
