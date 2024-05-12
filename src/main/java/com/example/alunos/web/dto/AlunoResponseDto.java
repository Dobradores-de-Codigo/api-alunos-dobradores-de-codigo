package com.example.alunos.web.dto;


import com.example.alunos.entities.Aluno;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;

@Getter @Setter@NoArgsConstructor@AllArgsConstructor
public class AlunoResponseDto {

    private String nome;
    private Aluno.Genero sexo;
    private boolean ativo;
}
