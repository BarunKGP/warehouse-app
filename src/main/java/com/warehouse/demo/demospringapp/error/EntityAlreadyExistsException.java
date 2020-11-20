package com.warehouse.demo.demospringapp.error;

public class EntityAlreadyExistsException extends RuntimeException {

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

    public EntityAlreadyExistsException(String message) {
        this.message = message;
    }

    public EntityAlreadyExistsException(String message, String debugMessage) {
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public EntityAlreadyExistsException(String message, String debugMessage, String details) {
        this.message = message;
        this.debugMessage = debugMessage;
        this.details = details;
    }
}
