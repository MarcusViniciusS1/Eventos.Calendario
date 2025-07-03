package com.eventos.calendario.repository;

import com.eventos.calendario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameAndAtivo(String username, Boolean ativo);

    boolean existsByUsername(String username);
}