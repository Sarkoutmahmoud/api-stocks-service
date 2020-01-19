package nl.mahmoud.sarkout.stock.models.api;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class Stock {
    private final int id;
    private final String name;
    private final long currentPrice;
    private final ZonedDateTime lastUpdate;
}
