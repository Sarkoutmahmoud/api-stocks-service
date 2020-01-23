package nl.mahmoud.sarkout.integration;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@AllArgsConstructor
public class Response {
    private Map<String, Object> body;
    private int status;
}
