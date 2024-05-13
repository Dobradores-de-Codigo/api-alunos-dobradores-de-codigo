package com.example.alunos.curso;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( name = "cursos", url = "http://localhost:8080/api/v1/cursos" )
public interface ConectarCurso {
    @GetMapping("/{id}")
    Curso getCurso(@PathVariable Long id);
}
