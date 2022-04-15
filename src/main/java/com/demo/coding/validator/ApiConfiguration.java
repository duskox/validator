package com.demo.coding.validator;

import com.demo.coding.validator.api.errors.ApiWebExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.stream.Collectors;

@Configuration
@EnableWebFlux
public class ApiConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes,
                                                             WebProperties webProperties,
                                                             ServerProperties serverProperties,
                                                             ApplicationContext applicationContext,
                                                             ServerCodecConfigurer serverCodecConfigurer,
                                                             ObjectProvider<ViewResolver> viewResolvers) {
        var handler = new ApiWebExceptionHandler(
                errorAttributes,
                webProperties.getResources(),
                serverProperties.getError(),
                applicationContext
        );

        handler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        handler.setMessageWriters(serverCodecConfigurer.getWriters());
        handler.setMessageReaders(serverCodecConfigurer.getReaders());

        return handler;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver config = new ClassLoaderTemplateResolver();

        config.setPrefix("templates/");
        config.setSuffix(".html");
        config.setTemplateMode(TemplateMode.HTML);
        config.setCharacterEncoding("UTF-8");
        config.setOrder(0);
        config.setCheckExistence(true);
        config.setCacheable(false);

        return config;
    }
}
