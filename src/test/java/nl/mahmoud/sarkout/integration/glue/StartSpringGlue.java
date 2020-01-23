package nl.mahmoud.sarkout.integration.glue;

import io.cucumber.java.Before;
import nl.mahmoud.sarkout.integration.ScenarioContext;
import nl.mahmoud.sarkout.stock.StockApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {StockApplication.class, ScenarioContext.class}, loader = SpringBootContextLoader.class)
@ActiveProfiles("dev")
public class StartSpringGlue extends AbstractGlue {

    @Before
    public void theApiManagementServiceHasBeenStarted() {
        //just starts spring
    }
}
