package com.eventos.calendario.controller;

import com.eventos.calendario.dto.PerfilDTO;
import com.eventos.calendario.model.Usuario;
import com.eventos.calendario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class PerfilController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<PerfilDTO> obterPerfil() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) auth.getPrincipal();
            
            PerfilDTO perfil = new PerfilDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name(),
                usuario.getCreatedAt().toString()
            );
            
            return ResponseEntity.ok(perfil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<PerfilDTO> atualizarPerfil(@Valid @RequestBody PerfilDTO.AtualizarPerfilRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) auth.getPrincipal();
            
            PerfilDTO perfilAtualizado = usuarioService.atualizarPerfil(usuario.getId(), request);
            return ResponseEntity.ok(perfilAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}