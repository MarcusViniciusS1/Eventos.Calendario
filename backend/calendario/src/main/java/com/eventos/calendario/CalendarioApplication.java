package com.eventos.calendario;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
=======
import com.eventos.calendario.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1

@SpringBootApplication
public class CalendarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarioApplication.class, args);
    }
<<<<<<< HEAD
=======

    @Bean
    CommandLineRunner init(UsuarioService usuarioService) {
        return args -> {
            System.out.println("=== INICIALIZANDO SISTEMA DE CALENDÁRIO ===");
            System.out.println();


            try {
                usuarioService.criarUsuariosIniciais();

                System.out.println("🔐 CREDENCIAIS DE ACESSO DISPONÍVEIS:");
                System.out.println();
                System.out.println("👤 ADMINISTRADOR 1:");
                System.out.println("   Login: ADM");
                System.out.println("   Senha: ADM123");
                System.out.println();
                System.out.println("👤 ADMINISTRADOR 2:");
                System.out.println("   Login: admin");
                System.out.println("   Senha: admin");
                System.out.println();
                System.out.println("🌐 URLs do Sistema:");
                System.out.println("   Frontend: http://localhost:5173");
                System.out.println("   Backend:  http://localhost:8080");
                System.out.println();
                System.out.println("✅ Sistema inicializado com sucesso!");

            } catch (Exception e) {
                System.out.println("❌ Erro ao inicializar usuários: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("==========================================");
        };
    }
>>>>>>> 20594da14ce2d6cc9b904a468c0b85abe05e53e1
}