package com.demo.coding.validator.persistence.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_orders")
//@SecondaryTable(name = "parts", pkJoinColumns = @PrimaryKeyJoinColumn(name = "work_order_id"))
@Getter
public class WorkOrderDbo {

    @Id
    String id;

    @Column(name = "type")
    String type;

    @Column(name = "department")
    String department;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column(name = "analysis_date")
    LocalDate analysisDate;

    @Column(name = "test_date")
    LocalDate testDate;

    @Column(name = "currency")
    String currency;

    @Column(name = "cost")
    BigDecimal cost;

    @Column(name = "responsible_person")
    String responsiblePerson;

    @Column(name = "factory_name")
    String factoryName;

    @Column(name = "factory_order_number")
    String factoryOrderNumber;


}
