package com.example.alunos.web.dto;

import com.example.alunos.entities.Aluno;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AlunoCreateDto {

    private String nome;
    @Size(min = 11, max = 11)
    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascumento;
    private Aluno.Genero sexo;
}
