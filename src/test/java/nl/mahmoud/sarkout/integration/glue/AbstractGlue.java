package nl.mahmoud.sarkout.integration.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.mahmoud.sarkout.integration.Response;
import nl.mahmoud.sarkout.integration.ScenarioContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

abstract class AbstractGlue {

    @Autowired
    protected ScenarioContext scenarioContext;
    @LocalServerPort
    private int port;

    private HttpClient client = HttpClient.newHttpClient();

    private final ObjectMapper mapper = new ObjectMapper();

    void get(final String path) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(endpoint(path))
                .build();

        setResponse(request);
    }


    void post(final String path, final Object body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(body)))
                    .uri(endpoint(path))
                    .build();
            setResponse(request);
        } catch (JsonProcessingException e) {
            fail("Error occured during test.");
        }

    }

    private void setResponse(HttpRequest request) {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = client.send(request, BodyHandlers.ofString());
            Map<String, Object> responseBody = mapper.readValue(httpResponse.body(), Map.class);
            scenarioContext.setResponse(new Response(responseBody, httpResponse.statusCode()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private URI endpoint(final String path) {
        String SERVER_URL = "http://localhost";
        return URI.create(SERVER_URL + ":" + port + path);
    }

}
