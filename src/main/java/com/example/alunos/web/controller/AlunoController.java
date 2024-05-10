package com.example.alunos.web.controller;

import com.example.alunos.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    private final AlunoService alunoService;
}
