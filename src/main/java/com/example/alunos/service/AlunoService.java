package com.example.alunos.service;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.entities.Aluno;
import com.example.alunos.exception.AlunoUniqueViolationException;
import com.example.alunos.exception.EntityNotFoundException;
import com.example.alunos.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ConectarCurso conectarCurso;

    @Transactional(readOnly = true)
    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Número de Id não encontrado")
        );
        }

    @Transactional
    public Aluno salvar(Aluno aluno) {
        try {
            return alunoRepository.save(aluno);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new AlunoUniqueViolationException(String.format("Aluno {%s} já cadastrado", aluno.getNome()));
        }
    }

}
