package com.demo.coding.validator.api.dtos;

import lombok.Builder;
import lombok.Value;
import reactor.util.annotation.Nullable;

import java.util.Map;

@Value
@Builder
public class ErrorDto {

    /**
     * Reason an error occurred, written in capital letters
     * with underscores separating words.
     * (e.g. <code>TOO_MANY_DIGITS</code>)
     */
    String failureReason;

    /**
     * Additional details if available, map key written in
     * camel case, value written as regular english language.
     * (e.g. <code>someKey:Value describing additional details</code>
     */
    @Nullable
    Map<String, String> details;
}
