package com.example.alunos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "alunos")
@EntityListeners(AuditingEntityListener.class)
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(name = "data_nascimento", nullable = false, length = 11)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 1)
    private Genero sexo;

    public enum Genero {
        F,
        M
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(id, aluno.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
