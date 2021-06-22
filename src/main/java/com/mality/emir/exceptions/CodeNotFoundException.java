package com.mality.emir.exceptions;

public class CodeNotFoundException extends RuntimeException {

    public CodeNotFoundException() {
    }

    public CodeNotFoundException(String message) {
        super(message);
    }
}
