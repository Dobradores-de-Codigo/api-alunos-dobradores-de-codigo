package com.example.alunos.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MatriculaResponseDto {

    private Long id;
    private String cursoNome;
    private Long cursoId;
    private String alunoNome;
    private boolean ativo;


}
