package com.trianasalesianos.dam.Miarma.exceptions;

public class PublicacionNotFoundException extends StorageException{

    public PublicacionNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public PublicacionNotFoundException(String message) {
        super(message);
    }
}
