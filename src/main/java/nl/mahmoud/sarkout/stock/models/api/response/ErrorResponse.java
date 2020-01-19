package nl.mahmoud.sarkout.stock.models.api.response;

import lombok.Value;

@Value
public class ErrorResponse {

    private final String message;
}
