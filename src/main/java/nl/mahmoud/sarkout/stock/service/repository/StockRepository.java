package nl.mahmoud.sarkout.stock.service.repository;

import nl.mahmoud.sarkout.stock.models.entity.StockEntity;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<StockEntity, Integer> {
}
