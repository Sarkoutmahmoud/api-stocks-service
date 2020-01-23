DROP SCHEMA if exists prod;
CREATE SCHEMA IF NOT EXISTS prod;
USE prod;

-- -----------------------------------------------------
-- Table prod.product
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS prod.stock_entry
(
    id              INTEGER (100) NOT NULL,
    stock_id        VARCHAR(100) NOT NULL,
    current_price   INTEGER (100) NULL,
    time_of_entry   TIMESTAMP (6)  NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table prod.stock
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS prod.stock
(
    id    INTEGER (100)  NOT NULL,
    name_stock  VARCHAR(100)  NULL,
    PRIMARY KEY (id)
);

ALTER TABLE prod.stock_entry
    ADD FOREIGN KEY (STOCK_ID)
        REFERENCES STOCK(ID)

