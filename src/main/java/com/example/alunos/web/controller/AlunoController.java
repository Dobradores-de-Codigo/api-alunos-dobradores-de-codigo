package com.example.alunos.web.controller;

import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Aluno;
import com.example.alunos.service.AlunoService;
import com.example.alunos.web.dto.AlunoCreateDto;
import com.example.alunos.web.dto.AlunoResponseDto;
import com.example.alunos.web.dto.mapper.AlunoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoResponseDto> cadastrarAluno(@RequestBody AlunoCreateDto createDto) {
        Aluno aluno = alunoService.salvar(AlunoMapper.toAluno(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoMapper.toDto(aluno));
    }

}