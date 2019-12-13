package com.winux.languagepack.exceptions;

public class LocaleNotFoundException extends RuntimeException {
    public LocaleNotFoundException(String message) {
        super(message);
    }

    public LocaleNotFoundException(String message, Throwable cause) {
        super(message, cause);

    }
}
