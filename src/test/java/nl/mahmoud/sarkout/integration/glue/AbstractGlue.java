package nl.mahmoud.sarkout.integration.glue;

import io.restassured.http.ContentType;
import nl.mahmoud.sarkout.integration.ScenarioContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;

abstract class AbstractGlue {

    @Autowired
    protected ScenarioContext scenarioContext;
    @LocalServerPort
    private int port;

    void get(final String path) {
        var requestSpecs = given().when();
        scenarioContext.setResponse(requestSpecs.get(endpoint(path)).andReturn());
    }


    void post(final String path, final Object body, final Map<String, String> headers) {
        scenarioContext.setResponse(
            given().with()
                .contentType(ContentType.JSON)
                .headers(headers != null ? headers : Collections.emptyMap())
                .body(body)
                .when()
                .post(endpoint(path))
                .andReturn());
    }

    private String endpoint(final String path) {
        String SERVER_URL = "http://localhost";
        return SERVER_URL + ":" + port + path;
    }

}
