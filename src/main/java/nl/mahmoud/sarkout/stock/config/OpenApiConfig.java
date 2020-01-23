package nl.mahmoud.sarkout.stock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String API_STOCK_SERVICE = "API Stock Service";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(info())
                .tags(tags());
    }

    private Info info() {
        return new Info()
                .title(API_STOCK_SERVICE)
                .description("This service is used to retrieve, update and insert stock data.")
                .version("1.0.0");
    }

    private List<Tag> tags() {
        final Tag tag = new Tag().name(API_STOCK_SERVICE)
                .description("All requests related to the Stocks service");
        return Collections.singletonList(tag);
    }

}
