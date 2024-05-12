package com.example.alunos.entities;


import com.example.alunos.curso.Curso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "matriculas")
@EntityListeners(AuditingEntityListener.class)
public class Matricula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "aluno_id")
    private Aluno aluno;

    @Column
    (name = "curso_id")
    private Long cursoId;

    @Column(name = "ativo")
    private boolean ativo = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return Objects.equals(id, matricula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}