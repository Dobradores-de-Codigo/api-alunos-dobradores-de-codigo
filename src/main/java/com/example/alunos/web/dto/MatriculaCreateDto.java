package com.example.alunos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MatriculaCreateDto {

    private Long alunoId;
    private Long cursoId;
}
