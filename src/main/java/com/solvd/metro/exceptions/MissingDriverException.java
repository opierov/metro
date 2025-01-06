package com.solvd.metro.exceptions;

public class MissingDriverException extends RuntimeException {

    public MissingDriverException(String message) {
        super(message);
    }
}