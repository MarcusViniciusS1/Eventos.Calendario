package com.eventos.calendario.service;

import com.eventos.calendario.model.Evento;
import com.eventos.calendario.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class DataInitializationService {
    
    private final EventoRepository eventoRepository;
    
    public void criarEventosExemplo() {
        try {
            // Verificar se já existem eventos
            long totalEventos = eventoRepository.count();
            
            if (totalEventos == 0) {
                System.out.println("🎯 Criando eventos de exemplo...");
                
                // Criar eventos de exemplo
                Evento[] eventosExemplo = {
                    new Evento(
                        "Workshop de Spring Boot",
                        "Aprenda a desenvolver APIs REST com Spring Boot do zero ao avançado. Curso prático com exemplos reais.",
                        LocalDate.of(2024, 12, 20),
                        LocalTime.of(14, 0),
                        "Centro de Convenções - Sala A",
                        "Tech Academy"
                    ),
                    new Evento(
                        "Conferência de Microserviços",
                        "Arquitetura de microserviços na prática: padrões, ferramentas e melhores práticas para sistemas distribuídos.",
                        LocalDate.of(2024, 12, 22),
                        LocalTime.of(9, 0),
                        "Auditório Principal",
                        "DevOps Institute"
                    ),
                    new Evento(
                        "Meetup de Desenvolvedores Java",
                        "Networking e troca de experiências entre desenvolvedores Java. Apresentações sobre as últimas novidades da linguagem.",
                        LocalDate.of(2024, 12, 25),
                        LocalTime.of(19, 0),
                        "Coworking Space Downtown",
                        "Java Community"
                    ),
                    new Evento(
                        "Curso de PostgreSQL Avançado",
                        "Banco de dados avançado com PostgreSQL: otimização, índices, procedures e administração de banco.",
                        LocalDate.of(2025, 1, 15),
                        LocalTime.of(8, 30),
                        "Laboratório de Informática",
                        "Database Academy"
                    ),
                    new Evento(
                        "Hackathon 2025",
                        "Competição de desenvolvimento de software de 48 horas. Prêmios para as melhores soluções inovadoras.",
                        LocalDate.of(2025, 1, 20),
                        LocalTime.of(8, 0),
                        "Campus Universitário",
                        "Innovation Hub"
                    ),
                    new Evento(
                        "Workshop de React e TypeScript",
                        "Desenvolvimento frontend moderno com React, TypeScript e melhores práticas de desenvolvimento.",
                        LocalDate.of(2025, 2, 5),
                        LocalTime.of(14, 0),
                        "Centro de Treinamento Tech",
                        "Frontend Masters"
                    )
                };
                
                for (Evento evento : eventosExemplo) {
                    Evento eventoSalvo = eventoRepository.save(evento);
                    System.out.println("✅ Evento criado: " + eventoSalvo.getTitulo() + " (ID: " + eventoSalvo.getId() + ")");
                }
                
                System.out.println("🎉 " + eventosExemplo.length + " eventos de exemplo criados com sucesso!");
                
            } else {
                System.out.println("ℹ️ Já existem " + totalEventos + " eventos no banco de dados");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar eventos de exemplo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}