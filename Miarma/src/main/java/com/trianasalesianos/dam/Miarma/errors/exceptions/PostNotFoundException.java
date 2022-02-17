package com.trianasalesianos.dam.Miarma.errors.exceptions;

public class PostNotFoundException extends StorageException{
    public PostNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
