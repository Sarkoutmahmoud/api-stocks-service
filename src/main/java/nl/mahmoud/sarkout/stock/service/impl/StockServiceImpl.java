package nl.mahmoud.sarkout.stock.service.impl;

import com.google.common.collect.Lists;
import nl.mahmoud.sarkout.stock.exception.ApplicationException;
import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.request.CreateStockRequest;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.api.response.HistoricalStock;
import nl.mahmoud.sarkout.stock.models.api.response.HistoricalStockEntry;
import nl.mahmoud.sarkout.stock.models.entity.StockEntity;
import nl.mahmoud.sarkout.stock.models.entity.StockEntryEntity;
import nl.mahmoud.sarkout.stock.service.StockService;
import nl.mahmoud.sarkout.stock.service.repository.StockEntryRepository;
import nl.mahmoud.sarkout.stock.service.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final SqlTimeService sqlTimeService;
    private final StockEntryRepository stockEntryRepository;

    public StockServiceImpl(StockRepository stockRepository, SqlTimeService sqlTimeService, StockEntryRepository stockEntryRepository) {
        this.stockRepository = stockRepository;
        this.sqlTimeService = sqlTimeService;
        this.stockEntryRepository = stockEntryRepository;
    }

    @Override
    public AllStocksResponse getAllStocks() {
        return new AllStocksResponse(Lists.newArrayList(stockRepository.findAll()).stream()
                .map(this::toStockConverter)
                .collect(toList()));
    }

    @Override
    public Stock getStock(Integer stockId) {
        StockEntity stockEntity = getStockFromDb(stockId);
        return toStockConverter(stockEntity);
    }

    @Override
    public boolean stockExists(Integer stockId) {
        return stockRepository.existsById(stockId);
    }

    @Override
    public HistoricalStock getStockHistory(Integer stockId) {
        StockEntity stockEntity = getStockFromDb(stockId);
        return HistoricalStock.builder().id(stockEntity.getId())
                .name(stockEntity.getName())
                .entries(convertToHistoricalStockEntries(stockEntity.getPrices()))
                .build();
    }

    @Override
    public Stock updateStockPrice(Integer stockId, Long price) {
        StockEntity stockEntity = getStockFromDb(stockId);
        StockEntryEntity update = StockEntryEntity.builder()
                .price(price)
                .stock(stockId)
                .entryDate(sqlTimeService.getCurrentTimestamp())
                .build();
        stockEntryRepository.save(update);
        return getStock(stockId);
    }

    @Override
    public Stock createStock(CreateStockRequest request) {
        StockEntity stockEntity = StockEntity.builder()
                .name(request.getName())
                .build();
        StockEntity savedStockEntity = stockRepository.save(stockEntity);
        return updateStockPrice(savedStockEntity.getId(), request.getPrice());
    }

    private Stock toStockConverter(StockEntity stock) {
        Stock.StockBuilder stockBuilder = Stock.builder()
                .id(stock.getId())
                .name(stock.getName());
        if (!stock.getPrices().isEmpty()) {
            StockEntryEntity stockEntryEntity = stock.getPrices().get(stock.getPrices().size() - 1);
            stockBuilder.currentPrice(stockEntryEntity.getPrice()).lastUpdate(convertToZonedTimestamp(stockEntryEntity.getEntryDate()));
        }

        return stockBuilder.build();
    }

    private List<HistoricalStockEntry> convertToHistoricalStockEntries(List<StockEntryEntity> prices) {
        return prices.stream()
                .map(stockEntryEntity -> HistoricalStockEntry.builder()
                        .price(stockEntryEntity.getPrice())
                        .timestamp(convertToZonedTimestamp(stockEntryEntity.getEntryDate()))
                        .build())
                .collect(toList());
    }

    private ZonedDateTime convertToZonedTimestamp(Timestamp entryDate) {
        return ZonedDateTime.of(entryDate.toLocalDateTime(), ZoneId.of("GMT+1"));
    }

    private StockEntity getStockFromDb(Integer stockId) {
        Optional<StockEntity> optionalStockEntity = stockRepository.findById(stockId);
        if (optionalStockEntity.isEmpty()) {
            throw new ApplicationException("An unknown error has occurred.");
        }
        return optionalStockEntity.get();
    }
}
