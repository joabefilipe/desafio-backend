package com.example.dasafio_tecnico.exception;

import com.example.dasafio_tecnico.entity.Tarefa;
import org.springframework.data.jpa.domain.Specification;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(Integer id) {
        super("Tarefa com ID " + id + " não foi encontrada");
    }

    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}