package com.eventos.calendario.controller;

<<<<<<< HEAD
import com.eventos.calendario.jwt.TokenService;
import com.eventos.calendario.model.Usuarios;
import com.eventos.calendario.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
=======
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
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

<<<<<<< HEAD
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Procurar o usuário no banco de dados
        Usuarios usuario = usuariosRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se a senha está correta (implementar a verificação de senha)
        if (!usuario.getSenha().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        // Gerar o token real
        String token = null;
        try {
            token = tokenService.gerarToken(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Retornar o token gerado
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", usuario.getUsername()));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        // Extraímos o token da autorização
        String token = authHeader.replace("Bearer ", "");

        // Validar o token
        String username = tokenService.validaToken(token);
        if (username == null) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        // Retornar a resposta com dados do usuário
        Usuarios usuario = usuariosRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(new TokenValidationResponse(
                true, usuario.getUsername(), usuario.getId(), usuario.getUsername(), "Token válido"
        ));
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginResponse {
        private String token;
        private String type;
        private String username;

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
=======
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


            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            System.out.println("✅ Autenticação realizada com sucesso!");


            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginRequest.getUsername());


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
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
