package org.example.proyecto.exception;

public class InvalidUserFieldException extends RuntimeException {
    public InvalidUserFieldException(String message) {
        super(message);
    }
}