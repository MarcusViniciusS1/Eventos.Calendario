package com.eventos.calendario.controller;

import com.eventos.calendario.controller.dto.UsuariosRequest;
import com.eventos.calendario.controller.dto.UsuariosResponse;
import com.eventos.calendario.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuariosResponse> cadastroUsuario(@RequestBody UsuariosRequest usuario) {
        try {
            return ResponseEntity.ok(usuariosService.criarUsuario(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsuariosResponse> login(@RequestBody UsuariosRequest usuario) {
        try {
            return ResponseEntity.ok().body(usuariosService.login(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
