package com.example.dasafio_tecnico.service;

import com.example.dasafio_tecnico.DTO.TarefaDTO;
import com.example.dasafio_tecnico.DTO.TarefaRespostaDTO;
import com.example.dasafio_tecnico.ENUM.StatusEnum;
import com.example.dasafio_tecnico.entity.Tarefa;
import com.example.dasafio_tecnico.exception.TarefaNaoEncontradaException;
import com.example.dasafio_tecnico.mapper.TarefaMapper;
import com.example.dasafio_tecnico.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.dasafio_tecnico.exception.TarefaNaoEncontradaException.hasTitulo;
import static com.example.dasafio_tecnico.security.UsuarioAutenticado.getLogin;
import static com.example.dasafio_tecnico.specification.TarefaSpecification.hasStatus;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;

    public TarefaRespostaDTO cadastrarTarefa(TarefaDTO dto) {

        Tarefa tarefa = mapper.toEntity(dto);

        tarefa.setCriadoPor(getLogin());
        tarefa.setCriadoEm(LocalDateTime.now());

        Tarefa salva = repository.save(tarefa);
        return mapper.toResponseDTO(salva);
    }

    public Page<TarefaRespostaDTO> buscarTarefa(StatusEnum status, String titulo, Pageable pageable) {

        Specification<Tarefa> spec = hasStatus(status)
                .and(hasTitulo(titulo));

        Page<Tarefa> page = repository.findAll(spec, pageable);

        if (page.isEmpty()) {
            throw new TarefaNaoEncontradaException("Não foi encontrada nenhuma tarefa");
        }

        return page.map(mapper::toResponseDTO);
    }

}
