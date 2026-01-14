package com.example.dasafio_tecnico.entity;

import com.example.dasafio_tecnico.ENUM.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnum status;

    @Column(nullable = false, updatable = false)
    private String criadoPor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    private String editadoPor;

    private LocalDateTime editadoEm;
}

