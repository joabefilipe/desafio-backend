package com.example.dasafio_tecnico.exception;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String login) {
        super("Login já está cadastrado");
    }
}
