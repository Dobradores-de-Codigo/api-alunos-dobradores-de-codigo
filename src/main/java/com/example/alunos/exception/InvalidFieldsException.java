package com.example.alunos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidFieldsException extends RuntimeException {

    public InvalidFieldsException(String message) {
        super(message);
    }

    public BindingResult getBindingResult() {
        return null;
    }
}