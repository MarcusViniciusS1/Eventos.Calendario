package com.eventos.calendario.service;

import com.eventos.calendario.model.Usuario;
import com.eventos.calendario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Buscando usuário: " + email);
        
        Usuario usuario = usuarioRepository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> {
                    System.out.println("❌ Usuário não encontrado ou inativo: " + email);
                    return new UsernameNotFoundException("Usuário não encontrado: " + email);
                });
        
        System.out.println("✅ Usuário encontrado: " + usuario.getEmail() + " | Role: " + usuario.getRole());
        return usuario;
    }
}