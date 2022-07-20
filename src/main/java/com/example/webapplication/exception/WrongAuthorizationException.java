package com.example.webapplication.exception;

public class WrongAuthorizationException extends Exception {
    private String errorField;

    public WrongAuthorizationException(String message, String errorField) {
        super(message);
        this.errorField = errorField;
    }

    public WrongAuthorizationException(String message, Throwable throwable, String errorField) {
        super(message, throwable);
        this.errorField = errorField;
    }

    public WrongAuthorizationException(Throwable throwable, String errorField) {
        super(throwable);
        this.errorField = errorField;
    }

    public String getErrorField() {
        return errorField;
    }
}
