package com.example.dasafio_tecnico.repository;

import com.example.dasafio_tecnico.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuarios, String> {

    UserDetails findByLogin(String login);
}
