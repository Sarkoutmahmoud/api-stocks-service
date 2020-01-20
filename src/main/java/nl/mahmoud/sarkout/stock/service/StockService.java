package nl.mahmoud.sarkout.stock.service;

import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.api.response.HistoricalStock;

public interface StockService {

    AllStocksResponse getAllStocks();

    Stock getStock(Integer stockId);

    boolean stockExists(Integer stockId);

    HistoricalStock getStockHistory(Integer stockId);
}
