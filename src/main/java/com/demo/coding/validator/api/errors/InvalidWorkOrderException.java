package com.demo.coding.validator.api.errors;

import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Thrown in case an invalid work order has been sent for processing.
 */
public class InvalidWorkOrderException extends RuntimeException {

    private final String reason;
    private final Map<String, String> details;

    public InvalidWorkOrderException(String reason,
                                     Map<String, String> details) {
        this.reason = reason;
        this.details = details;
    }

    public String getReason() {
        return reason;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
