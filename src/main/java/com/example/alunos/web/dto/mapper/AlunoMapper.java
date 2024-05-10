package com.example.alunos.web.dto.mapper;

import com.example.alunos.entities.Aluno;
import com.example.alunos.web.dto.AlunoCreateDto;
import com.example.alunos.web.dto.AlunoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlunoMapper {

    public static Aluno toAluno(AlunoCreateDto dto){
        return new ModelMapper().map(dto, Aluno.class);
    }
    public static AlunoResponseDto toDto(Aluno aluno){
        return new ModelMapper().map(aluno, AlunoResponseDto.class);
    }
}
