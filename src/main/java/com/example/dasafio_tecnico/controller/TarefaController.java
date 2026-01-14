package com.example.dasafio_tecnico.controller;

import com.example.dasafio_tecnico.DTO.TarefaDTO;
import com.example.dasafio_tecnico.DTO.TarefaRespostaDTO;
import com.example.dasafio_tecnico.ENUM.StatusEnum;
import com.example.dasafio_tecnico.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(summary = "Criar nova tarefa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping
    public ResponseEntity<TarefaRespostaDTO> cadastrarTarefa(
            @RequestBody @Valid TarefaDTO tarefaDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tarefaService.cadastrarTarefa(tarefaDTO));
    }

    @Operation(summary = "Buscar tarefas")
    @GetMapping
    public ResponseEntity<Page<TarefaRespostaDTO>> buscarTarefa(
            @RequestParam(required = false) StatusEnum status,
            @RequestParam(required = false) String titulo,
            @PageableDefault(page = 0, size = 10, sort = "criadoEm") Pageable pageable
    ) {
        return ResponseEntity.ok(tarefaService.buscarTarefa(status, titulo, pageable));
    }

}
