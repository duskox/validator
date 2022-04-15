package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.persistence.WorkOrderStore;
import com.demo.coding.validator.services.WorkOrderProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/work-orders")
public class WorkOrdersController {

    private final WorkOrderProcessor workOrderProcessor;
    private final WorkOrderStore workOrderStore;

    public WorkOrdersController(WorkOrderProcessor workOrderProcessor,
                                WorkOrderStore workOrderStore) {
        this.workOrderProcessor = workOrderProcessor;
        this.workOrderStore = workOrderStore;
    }

    @PostMapping()
    public Mono<ResponseEntity<Object>> validateWorkOrder(@RequestBody WorkOrderDto workOrderDto) {
        return Mono
                .justOrEmpty(workOrderProcessor.validateWorkOrder(workOrderDto))
                .map(workOrder -> {
                    workOrderStore.saveWorkOrder(workOrder);
                    return ResponseEntity.ok().build();
                })
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping
    public Flux<WorkOrderDto> getWorkOrders() {
        return Flux.fromIterable(workOrderStore.getWorkOrders())
                .map(ControllerUtils::toDto);
    }

}
