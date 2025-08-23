package com.roberto.adocao_pets_spring_web.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super("Resposta não é válida: " + message);
    }
}