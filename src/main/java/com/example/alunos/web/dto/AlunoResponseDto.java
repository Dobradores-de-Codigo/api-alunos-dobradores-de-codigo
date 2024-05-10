package com.example.alunos.web.dto;


import com.example.alunos.entities.Aluno;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;

@Getter @Setter@NoArgsConstructor@AllArgsConstructor
public class AlunoResponseDto {

    private Long id;
    private String nome;
    private String cpf;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascumento;
    private Aluno.Genero sexo;
}
