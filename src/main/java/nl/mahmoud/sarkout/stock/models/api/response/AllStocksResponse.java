package nl.mahmoud.sarkout.stock.models.api.response;

import lombok.Value;
import nl.mahmoud.sarkout.stock.models.api.Stock;

import java.util.List;

@Value
public class AllStocksResponse {
    List<Stock> stocks;
}
