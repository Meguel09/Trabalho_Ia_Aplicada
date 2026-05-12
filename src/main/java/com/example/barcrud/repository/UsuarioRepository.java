package com.example.barcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barcrud.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring Boot cria a query (SELECT * FROM usuario WHERE email = ?) automaticamente!
    Usuario findByEmail(String email);
}