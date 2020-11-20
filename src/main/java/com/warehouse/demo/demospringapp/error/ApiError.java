package com.warehouse.demo.demospringapp.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError() {
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setTimestamp (LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;        
    }

    public ApiError(HttpStatus httpStatus) {
        this.setHttpStatus(httpStatus);
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, Throwable ex) {
        this.setHttpStatus(httpStatus);
        this.setMessage("Unexpected error");
        this.setDebugMessage(ex.getLocalizedMessage());
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable ex) {
        this.setHttpStatus(httpStatus);
        this.setMessage(message);
        this.setDebugMessage(ex.getLocalizedMessage());
        timestamp = LocalDateTime.now();
    }

    
}
