package nl.mahmoud.sarkout.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.api.response.ErrorResponse;
import nl.mahmoud.sarkout.stock.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Get all stock details")
    @ApiResponse(responseCode = "200", description = "Successfully returned list of stocks", content = @Content(schema = @Schema(implementation = AllStocksResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(value = "/stocks")
    public AllStocksResponse getApiAdvancedScopes() {
        return stockService.getAllStocks();
    }
}
