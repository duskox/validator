package com.demo.coding.validator.api;

import com.demo.coding.validator.api.dtos.PartDto;
import com.demo.coding.validator.api.dtos.ValidationResponseDto;
import com.demo.coding.validator.api.dtos.WorkOrderDto;
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

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WorkOrdersController.class)
class WorkOrdersControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    WorkOrderProcessor workOrderProcessor;

    @Test
    void given_when_then() {
        var partDto = partDtoBuilder()
                .build();
        var analysisDto = analysisWorkOrderBuilder()
                .parts(List.of(partDto))
                .build();
        var expectedResult = ValidationResponseDto
                .builder()
                .valid(true)
                .build();

        new Arranger()
                .withValidResponse(analysisDto, expectedResult);

        webTestClient
                .post()
                .uri("/work-orders")
                .body(BodyInserters.fromValue(analysisDto))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(ValidationResponseDto.class)
                .isEqualTo(expectedResult);
    }

    private WorkOrderDto.WorkOrderDtoBuilder analysisWorkOrderBuilder() {
        return WorkOrderDto
                .builder()
                .type(WorkOrderType.ANALYSIS.name())
                .department("Test department")
                .startDate("2020-08-13")
                .endDate("2020-08-16")
                .currency("USD")
                .cost("123.12");
    }

    private PartDto.PartDtoBuilder partDtoBuilder() {
        return PartDto
                .builder()
                .name("Part name")
                .count(1)
                .inventoryNumber("Some inventory number");
    }

    private class Arranger {
        public Arranger withValidResponse(WorkOrderDto workOrderDto,
                                          ValidationResponseDto validResponse) {
            given(workOrderProcessor.validateWorkOrder(workOrderDto))
                    .willReturn(validResponse);
            return this;
        }
    }
}