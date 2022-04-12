package com.demo.coding.validator.domain;

import com.demo.coding.validator.api.dtos.WorkOrderDto;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldValidation {

    private boolean valid = true;
    private Map<String, String> faultsMap = new HashMap<>();

    public String validateDepartment(WorkOrderDto workOrderDto) {
        if (Strings.isBlank(workOrderDto.getDepartment())) {
            faultsMap.put("department", "Field should not be blank");
            valid = false;
        }
        return workOrderDto.getDepartment();
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, String> getFaultsMap() {
        return faultsMap;
    }

    public LocalDate validateStartDate(WorkOrderDto workOrderDto) {
        var startDate = extractStartDate(workOrderDto);

        if (startDate != null) {
            if (startDate.isBefore(LocalDate.now())) {
                return startDate;
            } else {
                faultsMap.put("startDate", "Should not be after current date");
                valid = false;
            }
        }

        return null;
    }

    public LocalDate validateEndDate(WorkOrderDto workOrderDto) {
        var startDate = extractStartDate(workOrderDto);
        var endDate = extractEndDate(workOrderDto);

        if (endDate != null && startDate != null) {
            if (endDate.isAfter(startDate)) {
                return endDate;
            } else {
                faultsMap.put("endDate", "Should not be before or equal to start date");
                valid = false;
            }
        }

        return null;
    }

    public Currency validateCurrency(WorkOrderDto workOrderDto) {
        try {
            return Currency.getInstance(workOrderDto.getCurrency());
        } catch (Exception e) {
            faultsMap.put("currency", e.getMessage());
            valid = false;
        }
        return null;
    }

    public BigDecimal validateCost(WorkOrderDto workOrderDto) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(workOrderDto.getCost()));
        } catch (Exception e) {
            faultsMap.put("cost", e.getMessage());
            valid = false;
        }
        return null;
    }

    public LocalDate validateAnalysisDate(WorkOrderDto workOrderDto) {
        var startDate = extractStartDate(workOrderDto);
        var endDate = extractEndDate(workOrderDto);
        var analysisDate = extractAnalysisDate(workOrderDto);

        if (startDate != null && endDate != null && analysisDate != null) {
            if (analysisDate.isBefore(startDate)) {
                faultsMap.put("analysisDate", "Before start date");
            } else if (analysisDate.isAfter(endDate)) {
                faultsMap.put("analysisDate", "After end date");
            } else {
                return analysisDate;
            }
        }

        return null;
    }

    public String validateResponsiblePerson(WorkOrderDto workOrderDto) {
        if (Strings.isBlank(workOrderDto.getResponsiblePerson())) {
            faultsMap.put("responsiblePerson", "Field should not be blank");
            valid = false;
        }
        return workOrderDto.getResponsiblePerson();
    }

    public LocalDate validateTestDate(WorkOrderDto workOrderDto) {
        var testDate = extractTestDate(workOrderDto);
        var endDate = extractEndDate(workOrderDto);
        var analysisDate = extractAnalysisDate(workOrderDto);

        if (testDate != null && endDate != null && analysisDate != null) {
            if (testDate.isBefore(analysisDate)) {
                faultsMap.put("testDate", "Before analysis date");
                valid = false;
            } else if (testDate.isAfter(endDate)) {
                faultsMap.put("testDate", "After end date");
                valid = false;
            } else {
                return testDate;
            }
        }
        return null;
    }

    public List<Part> validateAndExtractParts(WorkOrderDto workOrderDto) {
        if (workOrderDto.getParts().isEmpty()) {
            faultsMap.put("parts", "No parts defined");
            valid = false;
        }
        return workOrderDto.getParts()
                .stream()
                .map(FieldUtils::toDomainObject)
                .collect(Collectors.toList());
    }

    public String validateFactoryName(WorkOrderDto workOrderDto) {
        if (Strings.isBlank(workOrderDto.getFactoryName())) {
            faultsMap.put("factoryName", "Field should not be blank");
            valid = false;
        }
        return workOrderDto.getFactoryName();
    }

    public String validateFactoryOrderNumber(WorkOrderDto workOrderDto) {
        var validationPattern = "[a-zA-Z]{2}[0-9]{8}";
        if (!workOrderDto.getFactoryOrderNumber().matches(validationPattern)) {
            faultsMap.put("factoryName", "Invalid format");
            valid = false;
        }
        return workOrderDto.getFactoryOrderNumber();
    }

    public void validatePartsInventoryNumber(List<Part> parts) {
        parts.forEach(this::validateInventoryNumber);
    }

    private void validateInventoryNumber(Part part) {
        if (Strings.isBlank(part.getInventoryNumber())) {
            faultsMap.put("part", String.format("Part name: {} has blank inventory number", part.getName()));
            valid = false;
        }
    }

    private LocalDate extractStartDate(WorkOrderDto workOrderDto) {
        LocalDate startDate;
        if (Strings.isBlank(workOrderDto.getStartDate())) {
            faultsMap.put("startDate", "Field should not be blank");
            valid = false;
        }

        try {
            return LocalDate.parse(workOrderDto.getStartDate());
        } catch (Exception e) {
            faultsMap.put("startDate", e.getMessage());
            valid = false;
        }
        return null;
    }

    private LocalDate extractEndDate(WorkOrderDto workOrderDto) {
        LocalDate endDate;
        if (Strings.isBlank(workOrderDto.getStartDate())) {
            faultsMap.put("endDate", "Field should not be blank");
            valid = false;
        }

        try {
            return LocalDate.parse(workOrderDto.getStartDate());
        } catch (Exception e) {
            faultsMap.put("endDate", e.getMessage());
            valid = false;
        }
        return null;
    }

    private LocalDate extractAnalysisDate(WorkOrderDto workOrderDto) {
        LocalDate analysisDate;
        if (Strings.isBlank(workOrderDto.getAnalysisDate())) {
            faultsMap.put("analysisDate", "Field should not be blank");
            valid = false;
        }

        try {
            return LocalDate.parse(workOrderDto.getAnalysisDate());
        } catch (Exception e) {
            faultsMap.put("analysisDate", e.getMessage());
            valid = false;
        }
        return null;
    }

    private LocalDate extractTestDate(WorkOrderDto workOrderDto) {
        LocalDate testDate;
        if (Strings.isBlank(workOrderDto.getAnalysisDate())) {
            faultsMap.put("testDate", "Field should not be blank");
            valid = false;
        }

        try {
            return LocalDate.parse(workOrderDto.getAnalysisDate());
        } catch (Exception e) {
            faultsMap.put("testDate", e.getMessage());
            valid = false;
        }
        return null;
    }
}
