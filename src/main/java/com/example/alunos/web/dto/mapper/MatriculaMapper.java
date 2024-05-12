package com.example.alunos.web.dto.mapper;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.service.AlunoService;
import com.example.alunos.web.dto.MatriculaCreateDto;
import com.example.alunos.web.dto.MatriculaResponseDto;
import com.example.alunos.web.dto.MatriculasPorCursoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatriculaMapper {


    private static final ModelMapper modelMapper = new ModelMapper();

    public static Matricula toMatricula(MatriculaCreateDto dto, AlunoService alunoService, ConectarCurso curso) {
        Matricula matricula = new Matricula();
        matricula.setAluno(alunoService.buscarAlunoPorId(dto.getAlunoId()));
        matricula.setCursoId(curso.getCurso(dto.getCursoId()).id());
        matricula.setCursoNome(curso.getCurso(dto.getCursoId()).nome());
        return matricula;
    }

    public static MatriculaResponseDto toDto(Matricula matricula, AlunoService alunoService, ConectarCurso curso) {
        MatriculaResponseDto matriculaResponseDto = new MatriculaResponseDto();
        matriculaResponseDto.setAlunoNome(matricula.getAluno().getNome());
        matriculaResponseDto.setCursoId(matricula.getCursoId());
        matriculaResponseDto.setCursoNome(curso.getCurso(matricula.getCursoId()).nome());
        return modelMapper.map(matricula, MatriculaResponseDto.class);
    }

}