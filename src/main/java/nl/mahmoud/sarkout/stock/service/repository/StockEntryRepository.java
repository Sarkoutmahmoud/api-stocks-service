package nl.mahmoud.sarkout.stock.service.repository;

import nl.mahmoud.sarkout.stock.models.entity.StockEntryEntity;
import org.springframework.data.repository.CrudRepository;

public interface StockEntryRepository extends CrudRepository<StockEntryEntity, Integer> {
}
