package nl.mahmoud.sarkout.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import nl.mahmoud.sarkout.stock.models.api.Stock;
import nl.mahmoud.sarkout.stock.models.api.response.AllStocksResponse;
import nl.mahmoud.sarkout.stock.models.api.response.ErrorResponse;
import nl.mahmoud.sarkout.stock.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
public class StockController {

    public static final String STOCK_ID = "stockId";
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
        if(stockService.stockExists(stockId)){
            return ResponseEntity.ok().body(stockService.getStock(stockId));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("The stock could not be found."));
    }

    @Operation(summary = "Retrieve a specific stock by id.")
    @ApiResponse(responseCode = "200", description = "Successfully returned a stock with it's full history.", content = @Content(schema = @Schema(implementation = Stock.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Stock not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "500", description = "An internal server error has occurred", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping(value = "/stocks/{stockId}/history")
    public ResponseEntity<?> getStockHistory(@Parameter(in = ParameterIn.PATH, name = STOCK_ID, description = "The associated stock id", required = true) @PathVariable(value = STOCK_ID) final Integer stockId) {
        if(stockService.stockExists(stockId)){
            return ResponseEntity.ok().body(stockService.getStockHistory(stockId));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("The stock could not be found"));
    }





}
