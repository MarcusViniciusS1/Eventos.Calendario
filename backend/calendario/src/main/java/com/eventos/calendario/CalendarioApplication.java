package com.eventos.calendario;

import com.eventos.calendario.service.UsuarioService;
import com.eventos.calendario.service.DataInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class CalendarioApplication {
    
    private final UsuarioService usuarioService;
    private final DataInitializationService dataInitializationService;

    public static void main(String[] args) {
        SpringApplication.run(CalendarioApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            try {
                System.out.println("=== INICIALIZANDO SISTEMA ===");
                
                // Verificar se o usuário admin existe, se não, criar
                usuarioService.criarUsuarioAdmin();
                
                // Aguardar um pouco para garantir que a transação foi commitada
                Thread.sleep(1000);
                
                // Verificar se o usuário foi criado corretamente
                usuarioService.buscarPorEmail("admin@admin.com")
                    .ifPresentOrElse(
                        usuario -> {
                            System.out.println("✅ Usuário admin verificado:");
                            System.out.println("   Email: " + usuario.getEmail());
                            System.out.println("   Nome: " + usuario.getNome());
                            System.out.println("   Role: " + usuario.getRole());
                            System.out.println("   Ativo: " + usuario.isEnabled());
                            System.out.println("   ID: " + usuario.getId());
                        },
                        () -> System.out.println("❌ Erro: Usuário admin não foi criado corretamente")
                    );
                
                // Criar eventos de exemplo se não existirem
                dataInitializationService.criarEventosExemplo();
                
                System.out.println();
                System.out.println("=== SISTEMA DE CALENDÁRIO INICIADO ===");
                System.out.println();
                System.out.println("🌐 URLs do Sistema:");
                System.out.println("   Frontend: http://localhost:3002");
                System.out.println("   Backend:  http://localhost:8080");
                System.out.println();
                System.out.println("💾 Banco de Dados:");
                System.out.println("   Tipo: PostgreSQL");
                System.out.println("   Database: calendario_eventos");
                System.out.println("   Host: localhost:5432");
                System.out.println();
                System.out.println("🔐 Sistema com autenticação JWT");
                System.out.println("👤 Usuário Admin: admin@admin.com / admin123");
                System.out.println("🔑 Token JWT com expiração de 24 horas");
                System.out.println();
                System.out.println("🧪 Teste de credenciais:");
                System.out.println("   GET http://localhost:8080/api/auth/test-credentials");
                System.out.println();
                System.out.println("✅ Sistema inicializado com sucesso!");
                System.out.println("======================================");
                
            } catch (Exception e) {
                System.err.println("❌ Erro durante a inicialização: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}