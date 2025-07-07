package com.eventos.calendario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Simular login bem-sucedido para qualquer credencial
        System.out.println("=== LOGIN SIMULADO ===");
        System.out.println("👤 Email: " + loginRequest.getUsername());
        System.out.println("✅ Login autorizado automaticamente");
        System.out.println("======================");
        
        return ResponseEntity.ok(new LoginResponse("fake-token", "Bearer", loginRequest.getUsername()));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        // Sempre retornar token válido
        return ResponseEntity.ok(new TokenValidationResponse(
            true, "admin@admin.com", 1L, "Administrador", "Token válido"
        ));
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String type;
        private String username;

        public LoginResponse() {}

        public LoginResponse(String token, String type, String username) {
            this.token = token;
            this.type = type;
            this.username = username;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }

    public static class TokenValidationResponse {
        private boolean valid;
        private String username;
        private Long userId;
        private String nome;
        private String message;

        public TokenValidationResponse(boolean valid, String username, Long userId, String nome, String message) {
            this.valid = valid;
            this.username = username;
            this.userId = userId;
            this.nome = nome;
            this.message = message;
        }

        // Getters e Setters
        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}