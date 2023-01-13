package com.example.pocketdictionary.model;

public class ErrorResponse implements WordDetailType{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    @Override
    public String getString() {
        return message;
    }
}
