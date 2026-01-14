package com.example.dasafio_tecnico.repository;

import com.example.dasafio_tecnico.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>, JpaSpecificationExecutor<Tarefa> {

}