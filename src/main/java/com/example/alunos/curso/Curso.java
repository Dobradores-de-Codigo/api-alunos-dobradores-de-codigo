package com.example.alunos.curso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record Curso(
    Long id,
    String nome,
    String professor,
    Boolean ativo
){
}
