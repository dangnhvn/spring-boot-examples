package com.example.icommerce.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.icommerce.constants.ErrorCode;
import com.example.icommerce.exceptions.AuthenticationException;
import com.example.icommerce.exceptions.ICommerceException;
import com.example.icommerce.models.ObjectError;
import com.example.icommerce.models.Response;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle all security exceptions.
     * @param e : authenticationException
     * @return response
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<?> handleAuthenticationException (AuthenticationException e) {
        return new Response<>(new ObjectError(ErrorCode.INVALID_TOKEN, e.getMessage()));
    }

    @ExceptionHandler(ICommerceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> handleBackFillException (ICommerceException e) {
        logger.error(e.getMessage(), e);
        return new Response<>(new ObjectError(e.getErrorCode(), e.getMessage()));
    }

    /**
     * Handle all validation from javax.validation.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ObjectError objectError = new ObjectError(ErrorCode.INVALID_REQUEST, String.join(", ", errors.values()));
        return this.handleExceptionInternal(ex, new Response<>(objectError), headers, status, request);
    }

    /**
     * Handle exceptions from json converted to java object.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity
     */
    protected ResponseEntity<Object> handleHttpMessageNotReadable (HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        String message = ex.getMessage();
        if ( ex.getCause().getCause() != null ) {
            message = ex.getCause().getCause().getMessage();
        }
        ObjectError objectError = new ObjectError(ErrorCode.INVALID_REQUEST, message);
        return this.handleExceptionInternal(ex, new Response<>(objectError), headers, status, request);
    }

    /**
     * Handle all runtime exceptions.
     * @param e : RuntimeException
     * @return response
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<?> handleRuntimeException (RuntimeException e) {
        logger.error(e.getMessage(), e);
        return new Response<>(new ObjectError(ErrorCode.INVALID_REQUEST, e.getMessage()));
    }
}
