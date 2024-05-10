package com.example.alunos.service;

import com.example.alunos.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
}
