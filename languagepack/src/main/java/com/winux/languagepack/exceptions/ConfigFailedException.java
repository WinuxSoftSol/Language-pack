package com.winux.languagepack.exceptions;

public class ConfigFailedException extends RuntimeException {
    public ConfigFailedException(String message) {
        super(message);
    }

    public ConfigFailedException(String message, Throwable cause) {
        super(message, cause);

    }
}
