package com.warehouse.demo.demospringapp.error;

public class EntityCouldNotBeCreatedException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message;
    private String debugMessage;
    private String details;

    public EntityCouldNotBeCreatedException() {
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EntityCouldNotBeCreatedException(String message) {
        this.setMessage(message);
    }

    public EntityCouldNotBeCreatedException(String message, String debugMessage) {
        this.setMessage(message);
        this.setDebugMessage(debugMessage);
    }

    public EntityCouldNotBeCreatedException(String message, String debugMessage, String details) {
        this.setMessage(message);
        this.setDebugMessage(debugMessage);
        this.setDetails(details);
    }
    
}
