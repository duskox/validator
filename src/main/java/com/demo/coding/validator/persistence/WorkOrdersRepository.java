package com.demo.coding.validator.persistence;

import com.demo.coding.validator.persistence.dbo.WorkOrderDbo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrdersRepository extends JpaRepository<WorkOrderDbo, String> {
}
