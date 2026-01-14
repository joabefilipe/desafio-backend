package com.example.dasafio_tecnico.mapper;

import com.example.dasafio_tecnico.DTO.TarefaDTO;
import com.example.dasafio_tecnico.DTO.TarefaRespostaDTO;
import com.example.dasafio_tecnico.entity.Tarefa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    Tarefa toEntity(TarefaDTO dto);
    TarefaRespostaDTO toResponseDTO(Tarefa entity);
}
