package com.example.alunos.service;

import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.exception.AlunoJaMatriculadoException;
import com.example.alunos.exception.CursoLotadoException;
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
        return matriculaRepository.findByAlunoId(alunoId);
    }
    @Transactional(readOnly = true)
    public Optional<Matricula> buscarMatriculaPorId(Long id) {
        try {
            return matriculaRepository.findById(id);
        }catch (DataAccessException e){
            throw new RuntimeException("Erro ao salvar a matricula", e);
        }
    }

    @Transactional(readOnly = true)
    public Curso buscarCursoPorId(Long id) {
        try {
            return conectarCurso.getCurso(id);
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar curso", e);
        }
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

}
