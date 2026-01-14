package com.example.dasafio_tecnico.DTO;

import com.example.dasafio_tecnico.ENUM.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TarefaRespostaDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private StatusEnum status;
}
