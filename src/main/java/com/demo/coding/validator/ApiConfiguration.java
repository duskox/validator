package com.demo.coding.validator;

import com.demo.coding.validator.api.errors.ApiWebExceptionHandler;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class ApiConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes,
                                                             WebProperties webProperties,
                                                             ServerProperties serverProperties,
                                                             ApplicationContext applicationContext) {
        return new ApiWebExceptionHandler(
                errorAttributes,
                webProperties.getResources(),
                serverProperties.getError(),
                applicationContext
        );
    }
}
