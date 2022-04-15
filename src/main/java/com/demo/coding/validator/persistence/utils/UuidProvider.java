package com.demo.coding.validator.persistence.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidProvider {
    public UUID create() {
        return UUID.randomUUID();
    }
}
