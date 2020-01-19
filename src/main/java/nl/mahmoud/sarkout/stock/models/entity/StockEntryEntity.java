package nl.mahmoud.sarkout.stock.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "stock_entry", schema = "prod")
public class StockEntryEntity {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stock;
    @Column(name = "current_price")
    private long price;
    @Column(name = "time_of_entry")
    private Timestamp entryDate;
}
