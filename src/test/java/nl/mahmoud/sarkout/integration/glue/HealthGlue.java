package nl.mahmoud.sarkout.integration.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

public class HealthGlue extends AbstractGlue {


    @When("I request the health endpoint of the API")
    public void iRequestTheHealthEndpointOfTheAPI() {
        get("/actuator/health");
    }

    @And("^the health check returns ([A-Z]+)$")
    public void theHealthCheckReturnsUP(String status) {
        Assertions.assertEquals(scenarioContext.getResponse().getBody().get("status"), status);
    }

    @And("^the health response contains no details$")
    public void theHealthResponseContainsNoDetails() {
        HashMap<String, Object> details = (HashMap<String, Object>) scenarioContext.getResponse().getBody().get("details");
         Assertions.assertFalse(details != null && ( details.containsKey("diskSpace") || details.containsKey("dataSource")));
    }


}