package com.example.dasafio_tecnico.DTO;

import com.example.dasafio_tecnico.ENUM.UsuarioRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioDTO(@NotBlank(message = "{auth.login.notblank}")
                          @Size(min = 3, max = 50, message = "{auth.login.size}")
                          @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "{auth.login.pattern}")
                          String login,

                                 @NotBlank(message = "{auth.password.notblank}")
                          @Size(min = 6, max = 100, message = "{auth.password.size}")
                          String password,

                                 @NotNull(message = "{auth.role.notnull}")
                                 UsuarioRole role) {
}
