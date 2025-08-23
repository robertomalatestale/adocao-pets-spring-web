package com.roberto.adocao_pets_spring_web.exceptions;

public class InvalidPetAgeException extends RuntimeException {
  public InvalidPetAgeException(String message) {
    super("Idade inválida: " + message);
  }
}