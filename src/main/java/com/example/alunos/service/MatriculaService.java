package com.example.alunos.service;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ConectarCurso conectarCurso;


    @Transactional(readOnly = true)
    public List<Matricula> getAlunoMatriculado(Long alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    @Transactional(readOnly = true)
    public List<Matricula> buscarMatriculaPorId(Long id) {
        return matriculaRepository.findByCursoId(id);
    }

    @Transactional(readOnly = true)
    public Curso buscarCursoPorId(Long id) {
        return conectarCurso.getCurso(id);
    }

    @Transactional(readOnly = true)
    public List<Matricula> getTodosOsAlunosMatriculados(Long id) {
        return matriculaRepository.findAll();
    }

    @Transactional
    public Matricula salvar(Matricula matricula) {
        List<Matricula> matriculaPorCurso = matriculaRepository.findByCursoId(matricula.getCursoId());
        long cont = matriculaPorCurso.stream().filter(Matricula::isAtivo).count();

        if (cont >= 10) {
            throw new RuntimeException(String.format("Curso lotado"));
        }

        List<Matricula> lista = getAlunoMatriculado(matricula.getAluno().getId());

        for (Matricula matriculaExistente : lista) {
            if (matriculaExistente.getAluno().getId().equals(matricula.getAluno().getId())) {
                throw new RuntimeException("Aluno já está matriculado");
            }
        }
        return matriculaRepository.save(matricula);
    }

    @Transactional(readOnly = true)
    public List<Matricula> consultarMatriculas(Long id) {
        List<Matricula> matriculas = getTodosOsAlunosMatriculados(id);
        return matriculas;
    }

}
