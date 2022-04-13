package com.demo.coding.validator.api.errors;

import com.demo.coding.validator.api.dtos.ErrorDto;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ApiWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public ApiWebExceptionHandler(ErrorAttributes errorAttributes,
                                  WebProperties.Resources resources,
                                  ErrorProperties errorProperties,
                                  ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    /**
     * Rendering error response as JSON.
     * For details @see DefaultErrorWebExceptionHandler#renderErrorResponse
     */
    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var error = getError(request);

        if (error instanceof InvalidWorkOrderException) {
            return handleInvalidWorkOrderError((InvalidWorkOrderException) error);
        }

        return super.renderErrorResponse(request);
    }

    // Error formatting handlers
    private Mono<ServerResponse> handleInvalidWorkOrderError(InvalidWorkOrderException ex) {
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .body(BodyInserters
                        .fromValue(ErrorDto
                                .builder()
                                .failureReason(ex.getReason())
                                .details(ex.getDetails())
                                .build()));
    }
}
