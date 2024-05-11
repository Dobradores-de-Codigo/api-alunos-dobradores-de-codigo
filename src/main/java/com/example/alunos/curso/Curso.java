package com.example.alunos.curso;

public record Curso(
    Long id,
    String nome,
    String professor,
    Boolean ativo
){
}
