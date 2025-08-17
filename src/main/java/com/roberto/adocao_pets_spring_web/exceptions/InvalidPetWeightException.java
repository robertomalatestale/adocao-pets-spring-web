package com.roberto.adocao_pets_spring_web.exceptions;

public class InvalidPetWeightException extends RuntimeException {
    public InvalidPetWeightException(String message) {
        super("Peso inválido: " + message);
    }
}