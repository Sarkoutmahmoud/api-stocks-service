DROP SCHEMA if exists prod;
CREATE SCHEMA IF NOT EXISTS prod;
USE prod;

-- -----------------------------------------------------
-- Table prod.product
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS prod.stock_entry
(
    id              integer (100) NOT NULL,
    stock_id        VARCHAR(100) NOT NULL,
    current_price   INTEGER (100) NULL,
    time_of_entry   DATE  NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table prod.stock
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS prod.stock
(
    id    integer (100)  NOT NULL,
    name_stock  VARCHAR(100)  NULL,
    PRIMARY KEY (id)
);

