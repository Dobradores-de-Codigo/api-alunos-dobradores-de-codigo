package com.example.alunos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MatriculaNotFoundException extends RuntimeException {

    public MatriculaNotFoundException(String message) {
        super(message);
    }
}