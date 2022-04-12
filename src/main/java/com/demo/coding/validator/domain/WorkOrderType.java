package com.demo.coding.validator.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.data.util.Pair.toMap;

public enum WorkOrderType {
    ANALYSIS,
    REPAIR,
    REPLACEMENT;

//    private final Map<String, WorkOrderType> stringToEnum = Stream.of(values())
//            .collect(toMap(Object::toString, e -> e));
//
//    public static Optional<WorkOrderType> fromString(String text) {
//        return Optional.ofNullable()
//        Arrays.stream(WorkOrderType.values())
//                .sequential()
//                .map(workOrderType -> {
//                    if (workOrderType.toString().equals(text)) {
//                        return workOrderType;
//                    }
//                })
//                .
//    }
}
