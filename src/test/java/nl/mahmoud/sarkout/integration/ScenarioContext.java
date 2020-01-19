package nl.mahmoud.sarkout.integration;

import io.restassured.response.Response;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ScenarioContext {

    private Response response;


    public void reset() {
        this.response = null;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public <Type> Type getResponseValue(String name) {
        return response.jsonPath().get(name);
    }

    public <Type> Type getResponseValue(String name, Class<Type> clazz) {
        return response.jsonPath().getObject(name, clazz);
    }
}

