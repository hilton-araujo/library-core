package com.biblioteca.gestao_biblioteca.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class ContentAlreadyExistsException extends Exception {
    private String statusCode;
    public ContentAlreadyExistsException(String message){
        super(message);
        this.statusCode = String.valueOf(HttpStatus.CONFLICT);
    }
}