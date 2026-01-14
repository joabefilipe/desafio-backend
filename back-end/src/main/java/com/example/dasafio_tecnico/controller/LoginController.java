package com.example.dasafio_tecnico.controller;

import com.example.dasafio_tecnico.DTO.AutenticacaoDTO;
import com.example.dasafio_tecnico.DTO.LoginRespostaDTO;
import com.example.dasafio_tecnico.entity.Usuarios;
import com.example.dasafio_tecnico.exception.CredenciaisInvalidasException;
import com.example.dasafio_tecnico.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginRespostaDTO> login(@RequestBody @Validated AutenticacaoDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    data.login(),
                    data.password()
            );
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.genereteToken((Usuarios) auth.getPrincipal());
            return ResponseEntity.ok(new LoginRespostaDTO(token));

        } catch (BadCredentialsException ex) {
            throw new CredenciaisInvalidasException();
        }
    }


}
