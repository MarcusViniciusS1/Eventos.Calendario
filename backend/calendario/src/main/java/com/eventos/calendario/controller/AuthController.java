package com.eventos.calendario.controller;

import com.eventos.calendario.dto.LoginRequest;
import com.eventos.calendario.dto.LoginResponse;
import com.eventos.calendario.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== NOVA TENTATIVA DE LOGIN ===");
            System.out.println("👤 Usuário: " + loginRequest.getUsername());
            System.out.println("🔑 Senha fornecida: " + loginRequest.getPassword());

            // Autenticar usando Spring Security com dados do banco
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            System.out.println("✅ Autenticação realizada com sucesso!");

            // Carregar detalhes do usuário do banco
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginRequest.getUsername());

            // Gerar token JWT
            final String token = jwtUtil.generateToken(userDetails);

            System.out.println("🎫 Token JWT gerado com sucesso");
            System.out.println("🎉 Login concluído para: " + loginRequest.getUsername());
            System.out.println("================================");

            return ResponseEntity.ok(new LoginResponse(token, "Bearer", loginRequest.getUsername()));

        } catch (BadCredentialsException e) {
            System.out.println("❌ ERRO: Credenciais inválidas");
            System.out.println("   Usuário: " + loginRequest.getUsername());
            System.out.println("   Motivo: Senha incorreta ou usuário não existe");
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Usuário ou senha incorretos"));

        } catch (DisabledException e) {
            System.out.println("❌ ERRO: Conta desabilitada");
            System.out.println("   Usuário: " + loginRequest.getUsername());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Conta desabilitada"));

        } catch (Exception e) {
            System.out.println("❌ ERRO GERAL durante login:");
            System.out.println("   Usuário: " + loginRequest.getUsername());
            System.out.println("   Erro: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Erro interno do servidor"));
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