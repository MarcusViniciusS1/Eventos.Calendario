package com.eventos.calendario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CalendarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendarioApplication.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("=== SISTEMA DE CALENDÁRIO INICIADO ===");
            System.out.println();
            System.out.println("🌐 URLs do Sistema:");
            System.out.println("   Frontend: http://localhost:3000");  // Atualizar para a porta correta do frontend
            System.out.println("   Backend:  http://localhost:8080");
            System.out.println();
            System.out.println("💾 Banco de Dados:");
            System.out.println("   Tipo: PostgreSQL");
            System.out.println("   Database: calendario_eventos");
            System.out.println("   Host: localhost:5432");
            System.out.println();
            System.out.println("🔓 Sistema sem autenticação JWT");
            System.out.println("✅ Acesso livre ao painel administrativo");
            System.out.println();
            System.out.println("✅ Sistema inicializado com sucesso!");
            System.out.println("======================================");
        };
    }
}