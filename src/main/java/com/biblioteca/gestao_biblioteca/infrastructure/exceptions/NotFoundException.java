package com.biblioteca.gestao_biblioteca.infrastructure.exceptions;

public class NotFoundException extends Exception {
  public NotFoundException(String message){
    super(message);
  }
}