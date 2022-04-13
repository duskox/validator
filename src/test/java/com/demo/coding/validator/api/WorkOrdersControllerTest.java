package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.ErrorDto;
import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
import com.demo.coding.validator.domain.FieldValidation;
import com.demo.coding.validator.domain.WorkOrderType;
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

import java.util.List;
import java.util.Map;

import static com.demo.coding.validator.TestUtils.analysisWorkOrderBuilder;
import static com.demo.coding.validator.TestUtils.partDtoBuilder;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WorkOrdersController.class)
class WorkOrdersControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    WorkOrderProcessor workOrderProcessor;

    @Test
    void givenValidPayload_whenValidating_thenReturnOk() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();

        new Arranger()
                .withValidResponse(analysisDto, true);

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenInvalidPayload_whenValidating_thenReturnErrorDetails() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();
        var errorDetails = Map.of(
                FieldValidation.DEPARTMENT, FieldValidation.FIELD_SHOULD_NOT_BE_BLANK,
                FieldValidation.START_DATE, "Should not be after current date"
        );

        new Arranger()
                .withValidResponse(analysisDto, false);

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }



    private class Arranger {
        public Arranger withValidResponse(WorkOrderDto workOrderDto,
                                          boolean response) {
            given(workOrderProcessor.validateWorkOrder(workOrderDto))
                    .willReturn(response);
            return this;
        }
    }
}