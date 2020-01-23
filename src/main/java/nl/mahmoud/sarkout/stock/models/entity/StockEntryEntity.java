package nl.mahmoud.sarkout.stock.models.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Builder
@Entity
@Table(name = "stock_entry", schema = "prod")
public class StockEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(name = "stock_id")
    private Integer stock;
    @Column(name = "current_price")
    private long price;
    @Column(name = "time_of_entry")
    private Timestamp entryDate;
}
