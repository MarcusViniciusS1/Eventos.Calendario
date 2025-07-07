package com.eventos.calendario.service;

import com.eventos.calendario.model.Usuario;
import com.eventos.calendario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criarUsuario(String username, String password, String nome) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existe: " + username);
        }

        String senhaEncriptada = passwordEncoder.encode(password);
        Usuario usuario = new Usuario(username, senhaEncriptada, nome);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsernameAndAtivo(username, true);
    }

    public void criarUsuariosIniciais() {

        if (!usuarioRepository.existsByUsername("ADM")) {
            criarUsuario("ADM", "ADM123", "Administrador Principal");
            System.out.println("✅ Usuário ADM criado com sucesso");
        }


        if (!usuarioRepository.existsByUsername("admin")) {
            criarUsuario("admin", "admin", "Administrador Secundário");
            System.out.println("✅ Usuário admin criado com sucesso");
        }
    }
}