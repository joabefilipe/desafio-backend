package com.example.dasafio_tecnico.specification;

import com.example.dasafio_tecnico.ENUM.StatusEnum;
import com.example.dasafio_tecnico.entity.Tarefa;
import org.springframework.data.jpa.domain.Specification;

public class TarefaSpecification {

    public static Specification<Tarefa> hasStatus(StatusEnum status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
