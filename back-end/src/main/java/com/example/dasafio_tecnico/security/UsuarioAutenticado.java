package com.example.dasafio_tecnico.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UsuarioAutenticado {

    private UsuarioAutenticado() {}

    public static String getLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return "SYSTEM";
        }

        return auth.getName();
    }
}
