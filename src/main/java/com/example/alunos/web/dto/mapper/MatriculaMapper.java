package com.example.alunos.web.dto.mapper;

import com.example.alunos.entities.Matricula;
import com.example.alunos.web.dto.MatriculaCreateDto;
import com.example.alunos.web.dto.MatriculaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatriculaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Matricula toMatricula(MatriculaCreateDto dto) {
        return modelMapper.map(dto, Matricula.class);
    }

    public static MatriculaResponseDto toDto(Matricula matricula) {
        return modelMapper.map(matricula, MatriculaResponseDto.class);
    }
}