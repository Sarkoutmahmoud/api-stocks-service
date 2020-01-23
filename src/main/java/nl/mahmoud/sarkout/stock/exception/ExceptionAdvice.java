package nl.mahmoud.sarkout.stock.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import nl.mahmoud.sarkout.stock.models.api.response.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Nonnull;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final String INVALID_INPUT_SUPPLIED_MESSAGE = "Invalid input supplied message: {}";

    /**
     * This method is called when an exception occurs when Spring is unable to create Object as a request parameter in an controller.
     */
    @Override
    @NonNull
    public ResponseEntity<Object> handleTypeMismatch(@NonNull TypeMismatchException ex,
                                                     @NonNull HttpHeaders headers,
                                                     @NonNull HttpStatus status,
                                                     @NonNull WebRequest request) {
        log.warn(INVALID_INPUT_SUPPLIED_MESSAGE, ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid input supplied."));
    }

    /**
     * This method is called when an exception occurs when Spring is unable to create Object as a request parameter in an controller.
     */
    @Override
    @NonNull
    public ResponseEntity<Object> handleServletRequestBindingException(@NonNull ServletRequestBindingException ex,
                                                                       @NonNull HttpHeaders headers,
                                                                       @NonNull HttpStatus status,
                                                                       @NonNull WebRequest request) {
        log.warn(INVALID_INPUT_SUPPLIED_MESSAGE, ex.getMessage(), ex);
        if (ex instanceof MissingRequestHeaderException) {
            MissingRequestHeaderException exception = (MissingRequestHeaderException) ex;
            return ResponseEntity.badRequest().body(new ErrorResponse(String.format("Header '%s' missing from request", exception.getHeaderName())));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid input supplied."));
    }

    /**
     * This method is called when an exception occurs when Spring is unable to create Object as the request Object. For example when providing an invalid Enum value.
     */
    @Override
    @NonNull
    public ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatus status,
                                                               @NonNull WebRequest request) {
        log.warn(INVALID_INPUT_SUPPLIED_MESSAGE, ex.getMessage(), ex);
        if (ex.getCause() instanceof InvalidFormatException) {
            String invalidObject = (String) ((InvalidFormatException) ex.getCause()).getValue();
            return ResponseEntity.badRequest().body(new ErrorResponse(String.format("Invalid input supplied in request body. The value '%s' is not valid.", invalidObject)));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid input supplied in request body."));
    }

    /**
     * This method is called when one of the constraints are not met defined in the POJO of a request object. Like a @NonNUll Annotation.
     */
    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(@Nonnull MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatus status,
                                                               @NonNull WebRequest request) {
        log.warn(INVALID_INPUT_SUPPLIED_MESSAGE, ex.getMessage(), ex);
        if (ex.getBindingResult().getFieldError() != null && ex.getBindingResult().getFieldError().getDefaultMessage() != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getBindingResult().getFieldError().getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Invalid input supplied in request body."));
    }

    /**
     * Method to handle uncaught exceptions.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        log.error("Uncaught exception found - HttpStatusCode: {}, Error message: {}", INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponse("An internal server error has occurred."));
    }
}
