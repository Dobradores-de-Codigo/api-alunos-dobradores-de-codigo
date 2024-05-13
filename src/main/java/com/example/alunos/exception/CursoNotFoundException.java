package com.example.alunos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CursoNotFoundException extends RuntimeException {

    public CursoNotFoundException(String message) {
        super(message);
    }
}