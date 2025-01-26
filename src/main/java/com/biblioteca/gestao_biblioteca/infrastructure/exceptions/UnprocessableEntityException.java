package com.biblioteca.gestao_biblioteca.infrastructure.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
