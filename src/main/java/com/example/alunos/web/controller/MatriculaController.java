package com.example.alunos.web.controller;


import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.service.AlunoService;
import com.example.alunos.service.MatriculaService;
import com.example.alunos.web.dto.MatriculaCreateDto;
import com.example.alunos.web.dto.MatriculaResponseDto;
import com.example.alunos.web.dto.mapper.MatriculaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final ConectarCurso curso;

    @PostMapping
    public ResponseEntity<MatriculaResponseDto> matricularAluno(@RequestBody MatriculaCreateDto createDto) {
        Matricula matricula = matriculaService.salvar(MatriculaMapper.toMatricula(createDto, alunoService, curso));
        return ResponseEntity.status(HttpStatus.CREATED).body(MatriculaMapper.toDto(matricula, alunoService, curso));
    }

}
