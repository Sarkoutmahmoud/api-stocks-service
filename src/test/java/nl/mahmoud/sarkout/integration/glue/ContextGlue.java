package nl.mahmoud.sarkout.integration.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static org.junit.Assert.fail;

public class ContextGlue extends AbstractGlue {

    @Then("^status code \"([^\"]*)\" is returned$")
    public void statusCodeIsReturned(String statusCode) {
        Assertions.assertEquals(Integer.parseInt(statusCode), scenarioContext.getResponse().getStatus());
    }

    @And("^the response contains$")
    public void theResponseContains(Map<String, String> parameters) {
        for (Map.Entry<String, String> input : parameters.entrySet()) {
            String expectedFieldName = input.getKey();
            String expectedFieldValue = input.getValue();

            Object actualValue = scenarioContext.getResponse().getBody().get(expectedFieldName);

            if (actualValue == null) {
                fail("Field '" + expectedFieldName + "' was null");
            }
            Assertions.assertEquals(expectedFieldValue, actualValue,
                "Field '" + expectedFieldName + "' did not have the expected value");
        }
    }
}
