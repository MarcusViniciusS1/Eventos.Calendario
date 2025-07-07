package com.eventos.calendario.controller;

import com.eventos.calendario.dto.AuthDTO;
import com.eventos.calendario.model.Usuario;
import com.eventos.calendario.service.TokenService;
import com.eventos.calendario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO.LoginRequest loginRequest) {
        try {
            System.out.println("=== TENTATIVA DE LOGIN ===");
            System.out.println("👤 Email: " + loginRequest.getUsername());
            
            // Verificar se o usuário existe primeiro
            Usuario usuarioExistente = usuarioService.buscarPorEmail(loginRequest.getUsername())
                .orElse(null);
            
            if (usuarioExistente == null) {
                System.out.println("❌ Usuário não encontrado: " + loginRequest.getUsername());
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Usuário não encontrado"));
            }
            
            if (!usuarioExistente.isEnabled()) {
                System.out.println("❌ Usuário inativo: " + loginRequest.getUsername());
                return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Usuário inativo"));
            }
            
            // Autenticar usuário
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            
            Authentication auth = authenticationManager.authenticate(authToken);
            Usuario usuario = (Usuario) auth.getPrincipal();
            
            // Gerar token JWT
            String token = tokenService.generateToken(usuario);
            
            System.out.println("✅ Login bem-sucedido para: " + usuario.getEmail());
            System.out.println("🔑 Token gerado com sucesso");
            System.out.println("👤 Role: " + usuario.getRole().name());
            System.out.println("========================");
            
            AuthDTO.LoginResponse response = new AuthDTO.LoginResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getRole().name(),
                usuario.getId()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            System.out.println("❌ Credenciais inválidas para: " + loginRequest.getUsername());
            System.out.println("========================");
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Email ou senha incorretos"));
        } catch (AuthenticationException e) {
            System.out.println("❌ Falha na autenticação: " + e.getMessage());
            System.out.println("========================");
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Falha na autenticação: " + e.getMessage()));
        } catch (Exception e) {
            System.out.println("❌ Erro interno: " + e.getMessage());
            e.printStackTrace();
            System.out.println("========================");
            return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Erro interno do servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDTO.RegisterRequest registerRequest) {
        try {
            System.out.println("=== REGISTRO DE USUÁRIO ===");
            System.out.println("👤 Nome: " + registerRequest.getNome());
            System.out.println("📧 Email: " + registerRequest.getEmail());
            
            AuthDTO.RegisterResponse response = usuarioService.criarUsuario(registerRequest);
            
            System.out.println("✅ Usuário registrado com sucesso");
            System.out.println("===========================");
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            System.out.println("❌ Erro no registro: " + e.getMessage());
            System.out.println("===========================");
            return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            System.out.println("❌ Erro interno: " + e.getMessage());
            e.printStackTrace();
            System.out.println("===========================");
            return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Erro interno do servidor"));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = tokenService.validateToken(token);
            
            if (email.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new AuthDTO.TokenValidationResponse(false, null, null, null, null, "Token inválido"));
            }
            
            Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElse(null);
                
            if (usuario == null) {
                return ResponseEntity.badRequest()
                    .body(new AuthDTO.TokenValidationResponse(false, null, null, null, null, "Usuário não encontrado"));
            }
            
            return ResponseEntity.ok(new AuthDTO.TokenValidationResponse(
                true, 
                usuario.getEmail(), 
                usuario.getId(), 
                usuario.getNome(),
                usuario.getRole().name(),
                "Token válido"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new AuthDTO.TokenValidationResponse(false, null, null, null, null, "Erro na validação do token"));
        }
    }

    @GetMapping("/test-credentials")
    public ResponseEntity<?> testCredentials() {
        try {
            System.out.println("🧪 Endpoint de teste de credenciais chamado");
            
            // Verificar se o usuário admin existe
            Usuario admin = usuarioService.buscarPorEmail("admin@admin.com").orElse(null);
            
            if (admin == null) {
                System.out.println("❌ Usuário admin não encontrado no banco");
                return ResponseEntity.ok("❌ Usuário admin não encontrado");
            }
            
            System.out.println("✅ Usuário admin encontrado e verificado");
            return ResponseEntity.ok("✅ Usuário admin encontrado: " + admin.getEmail() + 
                " | Ativo: " + admin.isEnabled() + 
                " | Role: " + admin.getRole().name());
                
        } catch (Exception e) {
            System.out.println("❌ Erro no teste de credenciais: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("Erro ao verificar credenciais: " + e.getMessage());
        }
    }

    public static class ErrorResponse {
        private String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
}