package com.eventos.calendario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email é obrigatório")
        private String username; // Mantendo username para compatibilidade
        
        @NotBlank(message = "Senha é obrigatória")
        private String password;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank(message = "Nome é obrigatório")
        private String nome;
        
        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email é obrigatório")
        private String email;
        
        @NotBlank(message = "Senha é obrigatória")
        private String senha;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String type;
        private String username;
        private String nome;
        private String role;
        private Long userId;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TokenValidationResponse {
        private boolean valid;
        private String username;
        private Long userId;
        private String nome;
        private String role;
        private String message;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterResponse {
        private String message;
        private Long userId;
        private String email;
        private String nome;
    }
}