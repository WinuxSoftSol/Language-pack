package com.winux.languagepack.exceptions;

public class InstanceAlreadyCreatedException extends RuntimeException {
    public InstanceAlreadyCreatedException(String message) {
        super(message);
    }

    public InstanceAlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);

    }
}
