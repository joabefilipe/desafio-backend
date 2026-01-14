package com.example.dasafio_tecnico.service;

import com.example.dasafio_tecnico.DTO.TarefaDTO;
import com.example.dasafio_tecnico.DTO.TarefaRespostaDTO;
import com.example.dasafio_tecnico.entity.Tarefa;
import com.example.dasafio_tecnico.mapper.TarefaMapper;
import com.example.dasafio_tecnico.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.dasafio_tecnico.security.UsuarioAutenticado.getLogin;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;

    public TarefaRespostaDTO salvarTarefa(TarefaDTO dto) {

        Tarefa tarefa = mapper.toEntity(dto);

        tarefa.setCriadoPor(getLogin());
        tarefa.setCriadoEm(LocalDateTime.now());

        Tarefa salva = repository.save(tarefa);
        return mapper.toResponseDTO(salva);
    }
}
