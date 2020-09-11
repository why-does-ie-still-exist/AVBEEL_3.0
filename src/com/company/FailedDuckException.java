package com.company;

public class FailedDuckException extends Exception {
    public FailedDuckException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
