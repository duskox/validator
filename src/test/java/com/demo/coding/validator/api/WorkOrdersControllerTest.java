package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.*;
import com.demo.coding.validator.persistence.WorkOrderStore;
import com.demo.coding.validator.services.WorkOrderProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static com.demo.coding.validator.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WorkOrdersController.class)
class WorkOrdersControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    WorkOrderProcessor workOrderProcessor;

    @MockBean
    WorkOrderStore workOrderStore;

    @Test
    void givenApiCall_whenValidationSuccessful_thenReturnOk() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderDtoBuilder()
                .parts(List.of(partDto))
                .build();

        new Arranger()
                .withValidatorResponse(analysisWorkOrder());

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenApiCall_whenValidationFailed_thenValidateBadRequest() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderDtoBuilder()
                .parts(List.of(partDto))
                .build();

        new Arranger()
                .withValidatorResponse(null);

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void givenWorkOrdersStored_whenGettingAll_thenValidateEverythingReturned() {
        var part01 = WorkOrderPart
                .builder()
                .name("partName")
                .inventoryNumber("inv01")
                .count(2)
                .build();

        var part02 = WorkOrderPart
                .builder()
                .name("partName")
                .inventoryNumber("inv02")
                .count(1)
                .build();

        var analysis = new Analysis("department",
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(2),
                Currency.getInstance("USD"),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02));

        var repair = new Repair("department",
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(2),
                Currency.getInstance("USD"),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02),
                LocalDate.now().minusDays(4),
                "responsiblePerson",
                LocalDate.now().minusDays(3));

        var replacement = new Replacement("department",
                LocalDate.now().minusDays(5),
                LocalDate.now().minusDays(2),
                Currency.getInstance("USD"),
                BigDecimal.valueOf(123.12),
                List.of(part01, part02),
                "factoryName",
                "factoryOrderNum");

        new Arranger()
                .withStoreReturningWorkOrders(List.of(analysis, repair, replacement));

//        var analysisDto = ControllerUtils.toDto(analysis);
//        var repairDto = ControllerUtils.toDto(repair);
//        var replacementDto = ControllerUtils.toDto(replacement);

        webTestClient
                .get()
                .uri("/work-orders")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBodyList(WorkOrderDto.class)
                .hasSize(3);
    }

    private class Arranger {
        public Arranger withValidatorResponse(WorkOrder workOrder) {
            given(workOrderProcessor.validateWorkOrder(any()))
                    .willReturn(workOrder);
            return this;
        }

        public Arranger withStoreReturningWorkOrders(List<WorkOrder> workOrders) {
            given(workOrderStore.getWorkOrders())
                    .willReturn(workOrders);
            return this;
        }
    }
}