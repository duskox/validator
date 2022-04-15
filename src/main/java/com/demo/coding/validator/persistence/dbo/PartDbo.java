package com.demo.coding.validator.persistence.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parts")
@Getter
public class PartDbo {

    @Id
    String id;

    @Column(name = "work_order_id")
    String workOrderId;

    @Column(name = "name")
    String name;

    @Column(name = "part_count")
    Integer count;

    @Column(name = "inventory_number")
    String inventoryNumber;

}
