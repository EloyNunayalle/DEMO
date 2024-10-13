package org.example.proyecto.exception;

public class UnauthorizeOperationException extends RuntimeException{
  public UnauthorizeOperationException(String message) {
    super(message);
  }
}