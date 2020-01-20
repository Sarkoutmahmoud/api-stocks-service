package nl.mahmoud.sarkout.stock.models.api.response;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Builder
@Value
public class HistoricalStockEntry {
    private final long price;
    private final ZonedDateTime timestamp;
}
