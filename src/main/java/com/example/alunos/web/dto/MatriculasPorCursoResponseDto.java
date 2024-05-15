package com.example.alunos.web.dto;


import com.example.alunos.entities.Aluno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter@NoArgsConstructor@AllArgsConstructor
public class MatriculasPorCursoResponseDto {

    private String cursoNome;
    private String professor;
    private long totalAlunos;
    private List<AlunoResponseDto> alunos = new ArrayList<>();
}
