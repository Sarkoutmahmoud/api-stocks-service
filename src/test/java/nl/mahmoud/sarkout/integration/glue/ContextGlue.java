package nl.mahmoud.sarkout.integration.glue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.val;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static org.junit.Assert.fail;

public class ContextGlue extends AbstractGlue {

    @Then("^status code \"([^\"]*)\" is returned$")
    public void statusCodeIsReturned(String statusCode) {
        Assertions.assertEquals(Integer.parseInt(statusCode), scenarioContext.getStatusCode());
    }

    @And("^the response contains$")
    public void theResponseContains(Map<String, String> parameters) {
        for (Map.Entry<String, String> input : parameters.entrySet()) {
            val expectedFieldName = input.getKey();
            val expectedFieldValue = input.getValue();

            val actualValue = scenarioContext.getResponseValue(expectedFieldName);

            if (actualValue == null) {
                fail("Field '" + expectedFieldName + "' was null");
            }
            Assertions.assertEquals(expectedFieldValue, actualValue,
                "Field '" + expectedFieldName + "' did not have the expected value");
        }
    }
}
