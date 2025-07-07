package com.eventos.calendario.service;

import com.eventos.calendario.controller.dto.UsuariosRequest;
import com.eventos.calendario.controller.dto.UsuariosResponse;
import com.eventos.calendario.model.Usuarios;
import com.eventos.calendario.jwt.TokenService;
import com.eventos.calendario.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public UsuariosResponse criarUsuario(UsuariosRequest usuarioRequest) {
        Optional<Usuarios> usuarioResult =
                usuariosRepository.findByUsername(usuarioRequest.getUsername());
        if(usuarioResult.isPresent()) {
            throw new RuntimeException("Usuario já existe na base");
        }

        Usuarios usuario = new Usuarios();
        usuario.setUsername(usuarioRequest.getUsername());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuario.setId(null);

        Usuarios usuariosPersistResult = usuariosRepository.save(usuario);

        UsuariosResponse retorno = new UsuariosResponse();
        retorno.setId(usuariosPersistResult.getId());
        retorno.setUsername(usuariosPersistResult.getUsername());

        return retorno;
    }

    public UsuariosResponse login(UsuariosRequest usuario) throws Exception {
        Optional<Usuarios> usuarioResult =
                usuariosRepository.findByUsername(usuario.getUsername());
        if(usuarioResult.isEmpty()) {
            throw new RuntimeException("Usuario não encontrado!");
        }

        Usuarios usuarioBanco = usuarioResult.get();

        if(passwordEncoder.matches(usuario.getSenha(), usuarioBanco.getSenha())) {
            UsuariosResponse response = new UsuariosResponse();
            response.setId(usuarioBanco.getId());
            response.setUsername(usuarioBanco.getUsername());
            response.setToken(tokenService.gerarToken(usuarioBanco));

            return response;
        }

        throw new RuntimeException("Senha invalida");

    }
}