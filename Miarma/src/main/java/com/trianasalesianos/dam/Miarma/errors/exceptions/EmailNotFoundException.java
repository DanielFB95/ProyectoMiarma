package com.trianasalesianos.dam.Miarma.errors.exceptions;

public class EmailNotFoundException extends StorageException{
    public EmailNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
