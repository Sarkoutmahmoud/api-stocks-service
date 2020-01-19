package nl.mahmoud.sarkout.stock.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.val;
import nl.mahmoud.sarkout.stock.models.api.request.CreateStockRequest;
import nl.mahmoud.sarkout.stock.models.api.response.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionAdviceTest {

    private final ExceptionAdvice classUnderTest = new ExceptionAdvice();
    private final HttpHeaders headers = new HttpHeaders();
    private HttpStatus httpStatus = HttpStatus.OK;
    @Mock
    WebRequest request;

    @Test
    void handleTypeMismatch() {

        val invalid = new TypeMismatchException("inval", CreateStockRequest.class);

        val objectResponseEntity = classUnderTest.handleTypeMismatch(invalid, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        Assertions
            .assertEquals("Invalid input supplied.", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handleHttpMessageNotReadable() {
        var ex = new HttpMessageNotReadableException("");

        val objectResponseEntity = classUnderTest.handleHttpMessageNotReadable(ex, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals("Invalid input supplied in request body.", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handleHttpMessageNotReadableInvalidFormatException() {
        var ex = new HttpMessageNotReadableException("", new InvalidFormatException("", "value", null));

        var objectResponseEntity = classUnderTest.handleHttpMessageNotReadable(ex, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals("Invalid input supplied in request body. The value 'value' is not valid.", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handleMethodArgumentNotValidHappyFlow() {

        var ex = Mockito.mock(MethodArgumentNotValidException.class);
        var bindingResult = Mockito.mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(new FieldError("", "", "email is not correct"));

        final ResponseEntity<Object> objectResponseEntity = classUnderTest.handleMethodArgumentNotValid(ex, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals("email is not correct", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handleMethodArgumentNotValidUnhappyFlowFieldErrorIsNull() {

        final MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        final BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(null);

        final ResponseEntity<Object> objectResponseEntity = classUnderTest.handleMethodArgumentNotValid(ex, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals("Invalid input supplied in request body.", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handleMethodArgumentNotValidUnhappyFlowDefaultMEssageIsNull() {

        final MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        final BindingResult bindingResult = Mockito.mock(BindingResult.class);
        final FieldError fieldError = Mockito.mock(FieldError.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getDefaultMessage()).thenReturn(null);

        final ResponseEntity<Object> objectResponseEntity = classUnderTest.handleMethodArgumentNotValid(ex, headers, httpStatus, request);

        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals("Invalid input supplied in request body.", ((ErrorResponse) objectResponseEntity.getBody()).getMessage());
    }

    @Test
    void handle() {
        final ResponseEntity<ErrorResponse> objectResponseEntity = classUnderTest.handle(new NullPointerException("runtime error"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
        assertEquals("An internal server error has occurred.", objectResponseEntity.getBody().getMessage());
    }
}