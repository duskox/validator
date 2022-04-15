package com.demo.coding.validator.persistence;

import com.demo.coding.validator.persistence.dbo.PartDbo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartsRepository extends JpaRepository<PartDbo, String> {
    List<PartDbo> findAllByWorkOrderId(String id);
}
