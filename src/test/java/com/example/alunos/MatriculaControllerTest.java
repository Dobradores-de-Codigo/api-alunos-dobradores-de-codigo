package com.example.alunos;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Aluno;
import com.example.alunos.entities.Matricula;
import com.example.alunos.exception.AlunoJaMatriculadoException;
import com.example.alunos.exception.CursoLotadoException;
import com.example.alunos.exception.MatriculaNotFoundException;
import com.example.alunos.service.AlunoService;
import com.example.alunos.service.MatriculaService;
import com.example.alunos.web.controller.MatriculaController;
import com.example.alunos.web.dto.MatriculaCreateDto;
import com.example.alunos.web.dto.MatriculaResponseDto;
import com.example.alunos.web.dto.mapper.AlunoMapper;
import com.example.alunos.web.dto.mapper.MatriculaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatriculaControllerTest {

    @Mock
    private MatriculaService matriculaService;

    @Mock
    private AlunoService alunoService;

    @Mock
    private ConectarCurso conectarCurso;

    @InjectMocks
    private MatriculaController matriculaController;

    @Mock
    private MatriculaMapper matriculaMapperMock;

    @Test
    public void testMatricularAlunoSucesso() {
        MatriculaCreateDto createDto = new MatriculaCreateDto();
        createDto.setCursoId(1L);
        createDto.setAlunoId(1L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João da Silva");

        Curso curso = new Curso(1L, "TI", "Professor", true);

        Matricula matricula = new Matricula();
        matricula.setId(1L);

        MatriculaResponseDto responseDto = new MatriculaResponseDto();
        responseDto.setId(1L);
        responseDto.setAtivo(true);
        responseDto.setCursoNome(curso.nome());
        responseDto.setAlunoNome(aluno.getNome());

        when(alunoService.findById(anyLong())).thenReturn(Optional.of(aluno));
        when(conectarCurso.getCurso(anyLong())).thenReturn(curso);
        when(matriculaService.salvar(any(Matricula.class))).thenReturn(matricula);
        when(MatriculaMapper.toDto(matricula, alunoService, conectarCurso)).thenReturn(responseDto);

        ResponseEntity<MatriculaResponseDto> response = matriculaController.matricularAluno(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(alunoService, times(1)).findById(anyLong());
        verify(conectarCurso, times(1)).getCurso(anyLong());
        verify(matriculaService, times(1)).salvar(any(Matricula.class));
        verify(matriculaMapperMock, times(1)).toDto(matricula, alunoService, conectarCurso);
        verifyNoMoreInteractions(alunoService, conectarCurso, matriculaService, matriculaMapperMock);
    }

    @Test
    public void testMatricularAlunoAlunoJaMatriculadoException() {
        MatriculaCreateDto createDto = new MatriculaCreateDto();
        createDto.setCursoId(1L);
        createDto.setAlunoId(1L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Curso curso = new Curso(1L, "TI", "Professor", true);

        when(alunoService.findById(anyLong())).thenReturn(Optional.of(aluno));
        when(conectarCurso.getCurso(anyLong())).thenReturn(curso);
        when(matriculaService.salvar(any(Matricula.class))).thenThrow(new AlunoJaMatriculadoException("Aluno já está matriculado"));

        assertThrows(AlunoJaMatriculadoException.class, () -> matriculaController.matricularAluno(createDto));
        verify(conectarCurso, times(1)).getCurso(anyLong());
        verify(alunoService, times(1)).findById(anyLong());
        verify(matriculaService, times(1)).salvar(any(Matricula.class));
        verifyNoMoreInteractions(alunoService, conectarCurso, matriculaService);
    }

    @Test
    public void testMatricularAlunoCursoLotadoException() {
        MatriculaCreateDto createDto = new MatriculaCreateDto();
        createDto.setCursoId(1L);
        createDto.setAlunoId(1L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Curso curso = new Curso(1L, "TI", "Professor", true);

        when(alunoService.findById(anyLong())).thenReturn(Optional.of(aluno));
        when(conectarCurso.getCurso(anyLong())).thenThrow(new CursoLotadoException("Curso Lotado"));

        assertThrows(CursoLotadoException.class, () -> matriculaController.matricularAluno(createDto));
        verify(conectarCurso, times(1)).getCurso(anyLong());
        verify(alunoService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(alunoService, conectarCurso);
    }

    @Test
    public void testMatricularAlunoMatriculaNotFoundException() {
        MatriculaCreateDto createDto = new MatriculaCreateDto();
        createDto.setCursoId(1L);
        createDto.setAlunoId(1L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);

        Curso curso = new Curso(1L, "TI", "Professor", true);

        when(alunoService.findById(anyLong())).thenReturn(Optional.of(aluno));
        when(conectarCurso.getCurso(anyLong())).thenReturn(curso);
        when(matriculaService.salvar(any(Matricula.class))).thenThrow(new MatriculaNotFoundException("Matrícula não encontrada"));


        assertThrows(MatriculaNotFoundException.class, () -> matriculaController.matricularAluno(createDto));
        verify(alunoService, times(1)).findById(anyLong());
        verify(conectarCurso, times(1)).getCurso(anyLong());
        verify(matriculaService, times(1)).salvar(any(Matricula.class));
        verifyNoMoreInteractions(alunoService, conectarCurso, matriculaService);
    }

}