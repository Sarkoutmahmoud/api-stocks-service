package nl.mahmoud.sarkout.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.request.CreateStockRequest;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.api.response.ErrorResponse;
import nl.mahmoud.sarkout.stock.service.StockService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/api")
public class StockController {

    public static final String STOCK_ID = "stockId";
    public static final String COULD_NOT_BE_FOUND_ERROR_MESSAGE = "The stock could not be found.";
    public static final ResponseEntity<ErrorResponse> COULD_NOT_BE_FOUND_ERROR_RESPONSE = ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(COULD_NOT_BE_FOUND_ERROR_MESSAGE));
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Returns a list of all stock.")
    @ApiResponse(responseCode = "200", description = "Successfully returned list of stocks.", content = @Content(schema = @Schema(implementation = AllStocksResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(value = "/stocks")
    public AllStocksResponse getStocks() {
        return stockService.getAllStocks();
    }

    @Operation(summary = "Retrieve a specific stock by id.")
    @ApiResponse(responseCode = "200", description = "Successfully returned stock.", content = @Content(schema = @Schema(implementation = Stock.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Stock not found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(value = "/stocks/{stockId}")
    public ResponseEntity<?> getStock(@Parameter(in = ParameterIn.PATH, name = STOCK_ID, description = "The associated stock id", required = true) @PathVariable(value = STOCK_ID) final Integer stockId) {
        if (stockService.stockExists(stockId)) {
            return ResponseEntity.ok().body(stockService.getStock(stockId));
        } else return COULD_NOT_BE_FOUND_ERROR_RESPONSE;
    }

    @Operation(summary = "Retrieve a specific stock history by id.")
    @ApiResponse(responseCode = "200", description = "Successfully returned a stock with it's full history.", content = @Content(schema = @Schema(implementation = Stock.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Stock not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(value = "/stocks/{stockId}/history")
    public ResponseEntity<?> getStockHistory(@Parameter(in = ParameterIn.PATH, name = STOCK_ID, description = "The associated stock id", required = true) @PathVariable(value = STOCK_ID) final Integer stockId) {
        if (stockService.stockExists(stockId)) {
            return ResponseEntity.ok().body(stockService.getStockHistory(stockId));
        } else return COULD_NOT_BE_FOUND_ERROR_RESPONSE;
    }

    @Operation(summary = "Updates the current price of a stock without overriding old stock prices.")
    @ApiResponse(responseCode = "200", description = "Successfully updated the stock.", content = @Content(schema = @Schema(implementation = Stock.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Stock could not be found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PutMapping(value = "/stocks/{stockId}/history")
    public ResponseEntity<?> addStockEntry(@Parameter(in = ParameterIn.PATH, name = STOCK_ID, description = "The associated stock id", required = true) @PathVariable(value = STOCK_ID) final Integer stockId,
                                           @Parameter(description = "The new price of the stock.", required = true) @RequestBody final Long price) {
        if (stockService.stockExists(stockId)) {
            return ResponseEntity.ok().body(stockService.updateStockPrice(stockId, price));
        } else return COULD_NOT_BE_FOUND_ERROR_RESPONSE;
    }

    @Operation(summary = "Creates an new Stock.")
    @ApiResponse(responseCode = "200", description = "Successfully created the stock.", content = @Content(schema = @Schema(implementation = Stock.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Stock could not be found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred.", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @PostMapping(value = "/stocks")
    public ResponseEntity<?> createStock(@Parameter(description = "The new price of the stock.", required = true) @RequestBody final CreateStockRequest request) {
            return ResponseEntity.ok().body(stockService.createStock(request));
    }


}
