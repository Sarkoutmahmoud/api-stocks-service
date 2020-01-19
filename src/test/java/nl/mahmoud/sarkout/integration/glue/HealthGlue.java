package nl.mahmoud.sarkout.integration.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.val;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class HealthGlue extends AbstractGlue {


    @When("I request the health endpoint of the API")
    public void iRequestTheHealthEndpointOfTheAPI() {
        get("/actuator/health");
    }

    @And("^the health check returns ([A-Z]+)$")
    public void theHealthCheckReturnsUP(String status) {
        Assertions.assertEquals(scenarioContext.getResponseValue("status", String.class), status);
    }

    @And("^the health response contains no details$")
    public void theHealthResponseContainsNoDetails() {
        val details = scenarioContext.getResponseValue("details", HashMap.class);
        val containsAny = containsDiskSpaceStatus(details) || containsDataSourceStatus(details);
        Assertions.assertFalse(containsAny);
    }

    private boolean containsDiskSpaceStatus(Map details) {
        return details != null && details.containsKey("diskSpace");
    }

    private boolean containsDataSourceStatus(Map details) {
        return details != null && details.containsKey("dataSource");
    }
}