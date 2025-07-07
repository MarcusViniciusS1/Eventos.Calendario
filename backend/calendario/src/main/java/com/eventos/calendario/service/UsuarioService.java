package com.eventos.calendario.service;

import com.eventos.calendario.dto.AuthDTO;
import com.eventos.calendario.dto.PerfilDTO;
import com.eventos.calendario.model.Usuario;
import com.eventos.calendario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthDTO.RegisterResponse criarUsuario(AuthDTO.RegisterRequest request) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        // Validar força da senha
        if (request.getSenha().length() < 6) {
            throw new RuntimeException("Senha deve ter pelo menos 6 caracteres");
        }

        // Criar novo usuário
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(Usuario.Role.USER); // Por padrão, novos usuários são USER
        usuario.setAtivo(true);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new AuthDTO.RegisterResponse(
                "Usuário criado com sucesso",
                usuarioSalvo.getId(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getNome()
        );
    }

    public PerfilDTO atualizarPerfil(Long userId, PerfilDTO.AtualizarPerfilRequest request) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se o novo email já está em uso por outro usuário
        if (!usuario.getEmail().equals(request.getEmail()) &&
                usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso por outro usuário");
        }

        // Atualizar dados básicos
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());

        // Atualizar senha se fornecida
        if (request.getSenhaAtual() != null && request.getNovaSenha() != null) {
            if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
                throw new RuntimeException("Senha atual incorreta");
            }

            if (request.getNovaSenha().length() < 6) {
                throw new RuntimeException("Nova senha deve ter pelo menos 6 caracteres");
            }

            usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return new PerfilDTO(
                usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getRole().name(),
                usuarioAtualizado.getCreatedAt().toString()
        );
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmailAndAtivoTrue(email);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void criarUsuarioAdmin() {
        try {
            System.out.println("🔧 Verificando e criando usuários admin...");

            // Lista de usuários admin para criar
            String[][] usuariosAdmin = {
                    {"Administrador", "admin@admin.com"},
                    {"Marcus", "Marcus@admin"},
                    {"Gabriel", "Gabriel@admin"},
                    {"Kelvin", "Kelvin@admin"}
            };

            for (String[] dadosUsuario : usuariosAdmin) {
                String nome = dadosUsuario[0];
                String email = dadosUsuario[1];

                if (!usuarioRepository.existsByEmail(email)) {
                    System.out.println("🔧 Criando usuário admin: " + email);

                    Usuario admin = new Usuario();
                    admin.setNome(nome);
                    admin.setEmail(email);
                    admin.setSenha(passwordEncoder.encode("admin123"));
                    admin.setRole(Usuario.Role.ADMIN);
                    admin.setAtivo(true);

                    Usuario adminSalvo = usuarioRepository.save(admin);
                    System.out.println("✅ Usuário admin criado: " + email + " / admin123");
                    System.out.println("   ID: " + adminSalvo.getId());
                    System.out.println("   Nome: " + adminSalvo.getNome());
                    System.out.println("   Role: " + adminSalvo.getRole());
                    System.out.println("   Ativo: " + adminSalvo.isEnabled());
                } else {
                    Usuario adminExistente = usuarioRepository.findByEmail(email).orElse(null);
                    if (adminExistente != null) {
                        System.out.println("ℹ️ Usuário admin já existe: " + email);
                        System.out.println("   ID: " + adminExistente.getId());
                        System.out.println("   Nome: " + adminExistente.getNome());
                        System.out.println("   Role: " + adminExistente.getRole());
                        System.out.println("   Ativo: " + adminExistente.isEnabled());
                    } else {
                        System.out.println("⚠️ Usuário " + email + " existe no banco mas não foi encontrado");
                    }
                }
            }

            System.out.println("🎯 Verificação de usuários admin concluída!");

        } catch (Exception e) {
            System.err.println("❌ Erro ao criar usuário admin: " + e.getMessage());
            e.printStackTrace();
        }
    }
}