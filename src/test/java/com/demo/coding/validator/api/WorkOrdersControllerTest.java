package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
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
    void givenApiCall_whenValidationSuccessful_thenReturnOk() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();

        new Arranger()
                .withValidatorResponseForPayload(true, analysisDto);

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
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();

        new Arranger()
                .withValidatorResponseForPayload(false, analysisDto);

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private class Arranger {
        public Arranger withValidatorResponseForPayload(boolean response,
                                                        WorkOrderDto workOrderDto) {
            given(workOrderProcessor.validateWorkOrder(workOrderDto))
                    .willReturn(response);
            return this;
        }
    }
}