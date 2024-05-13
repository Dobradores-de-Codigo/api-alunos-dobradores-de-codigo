package com.example.alunos.web.controller;

import com.example.alunos.curso.Curso;
import com.example.alunos.entities.Aluno;
import com.example.alunos.exception.AlunoJaMatriculadoException;
import com.example.alunos.service.AlunoService;
import com.example.alunos.web.dto.AlunoCreateDto;
import com.example.alunos.web.dto.AlunoResponseDto;
import com.example.alunos.web.dto.mapper.AlunoMapper;
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


@Tag(name = "Alunos", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um aluno.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @Operation(summary = "Cadastrar um novo aluno", description = "Recurso para cadastrar um novo aluno",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlunoResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Aluno já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<AlunoResponseDto> cadastrarAluno(@RequestBody AlunoCreateDto createDto) {
        try {
            Aluno aluno = alunoService.salvar(AlunoMapper.toAluno(createDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(AlunoMapper.toDto(aluno));
        }catch (Exception e){
            throw new AlunoJaMatriculadoException("Erro ao salvar aluno.");
        }
    }

}