package com.trianasalesianos.dam.Miarma.errors.exceptions;

public class RegisterException extends StorageException{
    public RegisterException(String message, Exception e) {
        super(message, e);
    }

    public RegisterException(String message) {
        super(message);
    }
}
