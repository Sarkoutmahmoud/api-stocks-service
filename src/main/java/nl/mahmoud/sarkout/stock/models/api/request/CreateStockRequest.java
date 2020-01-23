package nl.mahmoud.sarkout.stock.models.api.request;

import lombok.Value;

@Value
public final class CreateStockRequest {

    private final String name;
    private final long price;

}
