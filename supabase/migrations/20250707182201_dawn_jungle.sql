-- Script para resetar o banco de dados
-- Execute este script no PostgreSQL antes de iniciar a aplicação

-- Conectar ao banco calendario_eventos
\c calendario_eventos;

-- Remover todas as tabelas se existirem
DROP TABLE IF EXISTS eventos CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- Remover sequências se existirem
DROP SEQUENCE IF EXISTS eventos_seq CASCADE;
DROP SEQUENCE IF EXISTS usuarios_seq CASCADE;

-- Criar tabela de usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    ativo BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de eventos
CREATE TABLE eventos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    local VARCHAR(300) NOT NULL,
    organizador VARCHAR(150) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Inserir usuário admin padrão
INSERT INTO usuarios (nome, email, senha, role, ativo) 
VALUES ('Administrador', 'admin@admin.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdOIGGrNG5f8X9VG', 'ADMIN', true);

-- Inserir alguns eventos de exemplo
INSERT INTO eventos (titulo, descricao, data, hora, local, organizador) VALUES
('Workshop de Spring Boot', 'Aprenda a desenvolver APIs REST com Spring Boot', '2024-12-20', '14:00:00', 'Centro de Convenções - Sala A', 'Tech Academy'),
('Conferência de Microserviços', 'Arquitetura de microserviços na prática', '2024-12-22', '09:00:00', 'Auditório Principal', 'DevOps Institute'),
('Meetup de Desenvolvedores Java', 'Networking e troca de experiências', '2024-12-25', '19:00:00', 'Coworking Space Downtown', 'Java Community'),
('Curso de PostgreSQL', 'Banco de dados avançado com PostgreSQL', '2025-01-15', '08:30:00', 'Laboratório de Informática', 'Database Academy'),
('Hackathon 2025', 'Competição de desenvolvimento de software', '2025-01-20', '08:00:00', 'Campus Universitário', 'Innovation Hub');

-- Verificar dados inseridos
SELECT 'Usuários criados:' as info;
SELECT id, nome, email, role, ativo FROM usuarios;

SELECT 'Eventos criados:' as info;
SELECT id, titulo, data, hora, organizador FROM eventos ORDER BY data;

COMMIT;