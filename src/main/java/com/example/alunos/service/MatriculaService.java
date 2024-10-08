package com.example.alunos.service;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.exception.*;
import com.example.alunos.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final ConectarCurso conectarCurso;


    @Transactional(readOnly = true)
    public List<Matricula> getAlunoMatriculado(Long alunoId) {
        List<Matricula> matriculas = matriculaRepository.findByAlunoId(alunoId);
        return matriculas;
    }

    @Transactional(readOnly = true)
    public Curso buscarCursoPorId(Long id) {
        Curso curso = conectarCurso.getCurso(id);
        if (curso == null) {
            throw new CursoNotFoundException("Curso não encontrado para o ID " + id);
        }
        return curso;
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
            throw new CursoLotadoException(String.format("Curso lotado"));
        }

        List<Matricula> lista = getAlunoMatriculado(matricula.getAluno().getId());

        for (Matricula matriculaExistente : lista) {
            if (matriculaExistente.getAluno().getId().equals(matricula.getAluno().getId())) {
                throw new AlunoJaMatriculadoException("Aluno já está matriculado");
            }
        }

        return matriculaRepository.save(matricula);
    }

    @Transactional(readOnly = true)
    public List<Matricula> consultarMatriculas(Long id) {
        try {
            List<Matricula> matriculas = getTodosOsAlunosMatriculados(id);
            return matriculas;
        } catch (Exception e) {
            throw new MatriculaNotFoundException("Matrículas não encontradas para o curso com ID " + id);
        }
    }

    @Transactional
    public void inabilitarMatricula(Long id) {
        try {
            Matricula matricula = matriculaRepository.findById(id).orElseThrow(
                    () -> new MatriculaNotFoundException("Matrícula não encontrada com ID " + id)
            );
            matricula.setAtivo(false);
        } catch (Exception e) {
            throw new InvalidFieldsException("Erro ao inativar matrícula com ID " + id);
        }
    }
}
