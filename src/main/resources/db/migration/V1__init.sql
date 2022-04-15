CREATE TABLE work_orders (
    id VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    analysis_date DATE,
    test_date DATE,
    currency VARCHAR(3) NOT NULL,
    cost NUMERIC NOT NULL,
    responsible_person VARCHAR(255),
    factory_name VARCHAR(255),
    factory_order_number VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY ukwo_id (id)
);

CREATE TABLE parts (
    id VARCHAR(255) NOT NULL,
    work_order_id VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    part_count NUMERIC,
    inventory_number VARCHAR(255),
    FOREIGN KEY (work_order_id) REFERENCES work_orders(id),
    PRIMARY KEY (id),
    UNIQUE KEY ukp_id (id)
);