package com.example.alunos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlunoUniqueViolationException extends RuntimeException {

    public AlunoUniqueViolationException(String message) {
        super(message);
    }
}