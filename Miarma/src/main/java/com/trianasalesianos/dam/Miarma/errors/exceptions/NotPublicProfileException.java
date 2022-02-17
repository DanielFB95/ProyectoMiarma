package com.trianasalesianos.dam.Miarma.errors.exceptions;

public class NotPublicProfileException extends StorageException{
    public NotPublicProfileException(String message, Exception e) {
        super(message, e);
    }

    public NotPublicProfileException(String message) {
        super(message);
    }
}
