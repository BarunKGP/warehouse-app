package com.warehouse.demo.demospringapp.error;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;
    private String debugMessage;
    private String details;

    public String getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        this.setMessage(message);
    }

    public EntityNotFoundException(String message, String debugMessage) {
        this.setMessage(message);
        this.setDebugMessage(debugMessage);
    }

    public EntityNotFoundException(String message, String debugMessage, String details) {
        this.setMessage(message);
        this.setDebugMessage(debugMessage);
        this.setDetails(details);
    }
    
}
