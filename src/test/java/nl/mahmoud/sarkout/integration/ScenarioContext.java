package nl.mahmoud.sarkout.integration;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ScenarioContext {

    private Response response;

    public void reset() {
        this.response = null;
    }

}

