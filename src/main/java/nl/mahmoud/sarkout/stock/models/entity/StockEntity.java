package nl.mahmoud.sarkout.stock.models.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "stock", schema = "prod")
public final class StockEntity {

    @Id
    @Column(name = "id")
    private  int id;

    @Column(name = "name_stock")
    private  String name;

    @OneToMany(mappedBy = "stock")
    private  List<StockEntryEntity> prices;
}
