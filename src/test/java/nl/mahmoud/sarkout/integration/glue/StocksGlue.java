package nl.mahmoud.sarkout.integration.glue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.response.HistoricalStock;
import org.junit.jupiter.api.Assertions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StocksGlue extends AbstractGlue {

    private static final ZonedDateTime DATE_TIME = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));

    @When("I request all the stocks from the application")
    public void iRequestAllTheStocksFromTheApplication() {
        get("/api/stocks");
    }

    @And("the response contains a list of three stocks")
    public void theResponseContainsAListOfThreeStocks() {
        Assertions.assertEquals(3, ((List)scenarioContext.getResponse().getBody().get("stocks")).size());
    }

    @When("I request a stock with id {string}")
    public void iRequestAStockWithId(String id) {
        get("/api/stocks/" + id);
    }

    @And("the response contain a lastUpdate field")
    public void theResponseContainALastUpdateField() {
       assertNotNull( scenarioContext.getResponse().getBody().get("lastUpdate"));
    }

    @And("a proper stock Object is returned.")
    public void aProperStockObjectIsReturned() {
        ObjectMapper objectMapper = getMapper();
        Stock stock = objectMapper.convertValue(scenarioContext.getResponse().getBody(), Stock.class);
        assertEquals(1, stock.getId());
        assertEquals(3, stock.getCurrentPrice());
        assertEquals("Unilever N.V", stock.getName());
        assertEquals(DATE_TIME, stock.getLastUpdate());
    }

    private ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @And("a proper stock history Object is returned.")
    public void aProperHistoryStockObjectIsReturned() {
        ObjectMapper objectMapper = getMapper();
        HistoricalStock stock = objectMapper.convertValue(scenarioContext.getResponse().getBody(), HistoricalStock.class);
        assertEquals(1, stock.getId());
        assertEquals(1, stock.getEntries().size());
        assertEquals(3, stock.getEntries().get(0).getPrice());
        assertEquals(DATE_TIME, stock.getEntries().get(0).getTimestamp());
        assertEquals("Unilever N.V", stock.getName());
    }

    @When("I request a stock  history with id {string}")
    public void iRequestAStockHistoryWithId(String id) {
        get("/api/stocks/" + id + "/history");
    }
}
