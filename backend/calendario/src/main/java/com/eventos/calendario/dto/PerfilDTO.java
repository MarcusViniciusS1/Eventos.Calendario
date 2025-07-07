package com.eventos.calendario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    private Long id;
    private String nome;
    private String email;
    private String role;
    private String createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtualizarPerfilRequest {
        @NotBlank(message = "Nome é obrigatório")
        private String nome;
        
        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email é obrigatório")
        private String email;
        
        private String senhaAtual;
        private String novaSenha;
    }
}