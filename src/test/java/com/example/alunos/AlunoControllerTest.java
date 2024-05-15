package com.example.alunos;

import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Aluno;
import com.example.alunos.exception.AlunoJaMatriculadoException;
import com.example.alunos.service.AlunoService;
import com.example.alunos.web.controller.AlunoController;
import com.example.alunos.web.dto.AlunoCreateDto;
import com.example.alunos.web.dto.AlunoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlunoControllerTest {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarAluno() {

        AlunoCreateDto createDto = new AlunoCreateDto();
        createDto.setNome("João");

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");

        when(alunoService.salvar(any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<AlunoResponseDto> responseEntity = alunoController.cadastrarAluno(createDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        AlunoResponseDto responseDto = responseEntity.getBody();
        assertEquals(1L, responseDto.getId());
        assertEquals("João", responseDto.getNome());

        verify(alunoService, times(1)).salvar(any(Aluno.class));
    }

    @Test
    public void testInativarAluno() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Maria");

        when(alunoService.inabilitarAluno(1L)).thenReturn(aluno);

        ResponseEntity<AlunoResponseDto> responseEntity = alunoController.inativarAluno(1L);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());

        AlunoResponseDto responseDto = responseEntity.getBody();
        assertEquals(1L, responseDto.getId());
        assertEquals("Maria", responseDto.getNome());

        verify(alunoService, times(1)).inabilitarAluno(1L);
    }
}
