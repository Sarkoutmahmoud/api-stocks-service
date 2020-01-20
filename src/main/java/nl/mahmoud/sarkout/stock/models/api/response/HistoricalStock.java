package nl.mahmoud.sarkout.stock.models.api.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class HistoricalStock {

    private final int id;
    private final String name;
    private final List<HistoricalStockEntry> entries;
}
