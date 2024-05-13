package com.example.alunos.web.controller;


import com.example.alunos.curso.ConectarCurso;
import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Matricula;
import com.example.alunos.service.AlunoService;
import com.example.alunos.service.MatriculaService;
import com.example.alunos.web.dto.MatriculaCreateDto;
import com.example.alunos.web.dto.MatriculaResponseDto;
import com.example.alunos.web.dto.MatriculasPorCursoResponseDto;
import com.example.alunos.web.dto.mapper.AlunoMapper;
import com.example.alunos.web.dto.mapper.MatriculaMapper;
import com.example.alunos.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@Tag(name = "Matriculas", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de uma matrícula.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final ConectarCurso curso;

    @Operation(summary = "Criar uma nova matrícula", description = "Recurso para criar uma nova matrícula",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatriculaResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Matrícula já cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<MatriculaResponseDto> matricularAluno(@RequestBody MatriculaCreateDto createDto) {
        Matricula matricula = matriculaService.salvar(MatriculaMapper.toMatricula(createDto, alunoService, curso));
        return ResponseEntity.status(HttpStatus.CREATED).body(MatriculaMapper.toDto(matricula, alunoService, curso));
    }

    @Operation(summary = "Recuperar todas as matrículas por curso", description = "Recurso para recuperar todas as matrículas por curso",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatriculasPorCursoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/cursos/{id}")
    public ResponseEntity<MatriculasPorCursoResponseDto> todasAsMatriculas(@PathVariable Long id) {
        List<Matricula> matricula = matriculaService.consultarMatriculas(id);
        MatriculasPorCursoResponseDto matriculados = new MatriculasPorCursoResponseDto();
        matriculados.setCursoNome(curso.getCurso(matricula.get(0).getCursoId()).nome());
        matriculados.setProfessor(curso.getCurso(matricula.get(0).getCursoId()).professor());
        matriculados.setAlunos(matricula.stream().map(x -> AlunoMapper.toDto(x.getAluno())).collect(Collectors.toList()));
        matriculados.setTotalAlunos(matricula.stream().map(x -> AlunoMapper.toDto(x.getAluno())).count());
        return ResponseEntity.status(HttpStatus.OK).body(matriculados);
    }

}
