package dev.shashiirk.shopmate.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handler for handling common exceptions in the application.
 * This class provides methods to handle various types of exceptions and return appropriate error responses.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link BadRequestException} and returns a response with HTTP status 400 (Bad Request).
     *
     * @param ex The {@link BadRequestException} to be handled.
     * @return ResponseEntity with HTTP status 400 and an error response body.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse response = new ErrorResponse(
                "Bad Request",
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles {@link UnauthorizedException} and returns a response with HTTP status 401 (Unauthorized).
     *
     * @param ex The {@link UnauthorizedException} to be handled.
     * @return ResponseEntity with HTTP status 401 and an error response body.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse response = new ErrorResponse(
                "Unauthorized Access",
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handles {@link ForbiddenException} and returns a response with HTTP status 403 (Forbidden).
     *
     * @param ex The {@link ForbiddenException} to be handled.
     * @return ResponseEntity with HTTP status 403 and an error response body.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse response = new ErrorResponse(
                "Forbidden Access",
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Handles {@link NotFoundException} and returns a response with HTTP status 404 (Not Found).
     *
     * @param ex The {@link NotFoundException} to be handled.
     * @return ResponseEntity with HTTP status 404 and an error response body.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "Resource Not Found",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles {@link OperationFailedException} and returns a response with HTTP status 500 (Internal Server Error).
     *
     * @param ex The {@link OperationFailedException} to be handled.
     * @return ResponseEntity with HTTP status 500 and an error response body.
     */
    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorResponse> handleOperationFailedException(OperationFailedException ex) {
        ErrorResponse response = new ErrorResponse(
                "Operation Failed",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles {@link UnsupportedOperationException} and returns a response with HTTP status 501 (Not Implemented).
     *
     * @param ex The {@link UnsupportedOperationException} to be handled.
     * @return ResponseEntity with HTTP status 501 and an error response body.
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        ErrorResponse response = new ErrorResponse(
                "Unsupported Operation",
                HttpStatus.NOT_IMPLEMENTED.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a response with HTTP status 400 (Bad Request).
     *
     * @param ex The {@link MethodArgumentNotValidException} to be handled.
     * @return ResponseEntity with HTTP status 400 and an error response body.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });
        ErrorResponse response = new ErrorResponse(
                "Validation Failed",
                HttpStatus.BAD_REQUEST.value(),
                errors.toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException} and returns a response with HTTP status 405 (Method Not Allowed).
     *
     * @param ex The {@link HttpRequestMethodNotSupportedException} to be handled.
     * @return ResponseEntity with HTTP status 405 and an error response body.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse(
                "Method Not Allowed",
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Request method '" + ex.getMethod() + "' not supported"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles all unhandled exceptions and returns a response with HTTP status 500 (Internal Server Error).
     *
     * @param ex The Exception to be handled.
     * @return ResponseEntity with HTTP status 500 and an error response body.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error(
                "Exception with cause = '{}' and exception = '{}'",
                ex.getCause() != null ? ex.getCause() : "NULL",
                ex.getMessage(),
                ex
        );
        ErrorResponse response = new ErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Encapsulates error details such as error message, HTTP status code, and detail message.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public record ErrorResponse(String error, int status, String detail) {
    }
}
