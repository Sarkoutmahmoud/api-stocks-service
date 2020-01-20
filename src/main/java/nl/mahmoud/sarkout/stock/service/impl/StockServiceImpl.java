package nl.mahmoud.sarkout.stock.service.impl;

import com.google.common.collect.Lists;
import nl.mahmoud.sarkout.stock.exception.ApplicationException;
import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.entity.StockEntity;
import nl.mahmoud.sarkout.stock.models.entity.StockEntryEntity;
import nl.mahmoud.sarkout.stock.service.StockService;
import nl.mahmoud.sarkout.stock.service.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public AllStocksResponse getAllStocks() {
        return new AllStocksResponse(Lists.newArrayList(stockRepository.findAll()).stream()
                .map(this::toStockConverter)
                .collect(Collectors.toList()));
    }

    @Override
    public Stock getStock(Integer stockId) {
        Optional<StockEntity> optionalStockEntity = stockRepository.findById(stockId);
        if (optionalStockEntity.isEmpty()) {
            throw new ApplicationException("An unknown error has occurred.");
        }
        return toStockConverter(optionalStockEntity.get());
    }

    @Override
    public boolean stockExists(Integer stockId) {
        return stockRepository.existsById(stockId);
    }

    private Stock toStockConverter(StockEntity stock) {
        StockEntryEntity stockEntryEntity = stock.getPrices().get(stock.getPrices().size() - 1);
        Timestamp entryDate = stockEntryEntity.getEntryDate();
        return Stock.builder()
                .id(stock.getId())
                .name(stock.getName())
                .currentPrice(stockEntryEntity.getPrice())
                .lastUpdate(ZonedDateTime.of(entryDate.toLocalDateTime(), ZoneId.of("ECT")))
                .build();
    }
}
