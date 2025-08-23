package com.roberto.adocao_pets_spring_web.exceptions;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String message) {
        super("Nome inválido: " + message);
    }
}
