package com.example.alunos.repository;

import com.example.alunos.entities.Aluno;
import com.example.alunos.entities.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByAlunoId(long alunoId);

    List<Matricula> findByCursoId(Long cursoId);
}
