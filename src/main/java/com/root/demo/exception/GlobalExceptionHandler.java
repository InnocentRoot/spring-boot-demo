package com.root.demo.exception;

import com.root.demo.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.Exception;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ExceptionHandlerMethods.EntityNotFoundException.class)
    public final ResponseEntity handleNotFoundExceptions(Exception ex) {
        return this.formatError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandlerMethods.DuplicateEntityException.class)
    public final ResponseEntity handleDuplicateException(Exception ex) {
        return this.formatError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandlerMethods.UnSupportedFieldPatchException.class)
    public final ResponseEntity handleUnSupportedFieldPatchException(Exception ex) {
        return this.formatError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(ExceptionHandlerMethods.ServerException.class)
    public final ResponseEntity handleInternalServerError(Exception ex) {
        return this.formatError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity constraintViolationException(Exception ex) {
        return this.formatError(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    /**
     * Handle Request Body validation
     * @param ex Exception Instance
     * @param headers Headers content
     * @param status Http status
     * @param request API Request
     * @return fields validation error
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity AccessDeniedException(Exception ex) {
        return this.formatError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    /**
     * Format error message to send
     * @param status Http status
     * @param message Exception message
     * @return formatted error message
     */
    private ResponseEntity<ErrorResponse> formatError(HttpStatus status, String message) {
        ErrorResponse error = (new ErrorResponse())
                .setError(message)
                .setStatus(status.value())
                .setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, status);
    }
}
