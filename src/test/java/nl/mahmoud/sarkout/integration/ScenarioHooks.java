package nl.mahmoud.sarkout.integration;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ScenarioHooks {

    @Autowired
    ScenarioContext scenarioContext;

    @After
    public void tearDown(Scenario scenario) {
        log.info("Finished scenario [{}] with state [{}]", scenario.getName(), scenario.getStatus());
        scenarioContext.reset();
    }

    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting scenario [{}]", scenario.getName());
    }
}