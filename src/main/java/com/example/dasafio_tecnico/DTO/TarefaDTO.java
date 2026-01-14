package com.example.dasafio_tecnico.DTO;

import com.example.dasafio_tecnico.ENUM.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TarefaDTO {
    @NotBlank(message = "{tarefa.titulo.notblank}")
    private String titulo;

    @NotBlank(message = "{tarefa.descricao.notblank}")
    private String descricao;

    @NotNull(message = "{tarefa.data.notnull}")
    private LocalDateTime data;

    @NotNull(message = "{tarefa.status.notnull}")
    private StatusEnum status;
}
