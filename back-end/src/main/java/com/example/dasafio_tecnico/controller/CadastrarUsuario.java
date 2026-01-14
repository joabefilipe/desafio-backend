package com.example.dasafio_tecnico.controller;

import com.example.dasafio_tecnico.DTO.CadastroUsuarioDTO;
import com.example.dasafio_tecnico.entity.Usuarios;
import com.example.dasafio_tecnico.exception.UsuarioJaExisteException;
import com.example.dasafio_tecnico.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class CadastrarUsuario {

    private final UsuarioRepository repository;

    @PostMapping("/cadastro")
    public ResponseEntity<Void> register(@RequestBody @Valid CadastroUsuarioDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            throw new UsuarioJaExisteException(data.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuarios novoUsuario = new Usuarios(data.login(), encryptedPassword, data.role());

        this.repository.save(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
