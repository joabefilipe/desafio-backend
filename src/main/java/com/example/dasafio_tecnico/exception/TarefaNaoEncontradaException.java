package com.example.dasafio_tecnico.exception;

import com.example.dasafio_tecnico.entity.Tarefa;
import org.springframework.data.jpa.domain.Specification;

public class TarefaNaoEncontradaException extends RuntimeException {

    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public static Specification<Tarefa> hasTitulo(String titulo) {
        return (root, query, cb) ->
                titulo == null ? null :
                        cb.like(
                                cb.lower(root.get("titulo")),
                                "%" + titulo.toLowerCase() + "%"
                        );
    }
}