package com.warehouse.demo.demospringapp.error;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    
    @Override
    protected ResponseEntity<Object> 
    handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        logger.error("No such element found: ", ex.getMessage());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getDebugMessage());
        return buildResponseEntity(apiError);
    }   

    @ExceptionHandler(EntityCouldNotBeCreatedException.class)
    protected ResponseEntity<Object> handleEntityNotCreated(EntityCouldNotBeCreatedException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        logger.error("No such element found: ", ex.getMessage());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getDebugMessage());
        return buildResponseEntity(apiError);
    }  

    @ExceptionHandler(InvalidOrderException.class)
    protected ResponseEntity<Object> handleInvalidOrder(InvalidOrderException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        logger.error(ex.getMessage());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getDebugMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        logger.error(ex.getMessage());
        apiError.setMessage(ex.getMessage());
        apiError.setDebugMessage(ex.getDebugMessage());
        return buildResponseEntity(apiError);
    }

}
