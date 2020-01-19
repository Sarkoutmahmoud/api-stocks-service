package nl.mahmoud.sarkout.stock.models.api.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void testEquals() {
        EqualsVerifier.forClass(ErrorResponse.class).verify();
    }
}