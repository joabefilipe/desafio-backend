package com.example.dasafio_tecnico.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AutenticacaoDTO(
        @NotBlank(message = "{auth.login.notblank}")
        @Size(min = 3, max = 50, message = "{auth.login.size}")
        String login,

        @NotBlank(message = "{auth.password.notblank}")
        @Size(min = 6, message = "{auth.password.size}")
        String password
) {}