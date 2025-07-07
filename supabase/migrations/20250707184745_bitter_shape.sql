-- Script para criar o usuário admin no banco de dados
-- Execute este script se o usuário admin não existir

-- Conectar ao banco calendario_eventos
\c calendario_eventos;

-- Verificar se o usuário admin já existe
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@admin.com') THEN
        -- Inserir usuário admin com senha criptografada (admin123)
        INSERT INTO usuarios (nome, email, senha, role, ativo, created_at, updated_at) 
        VALUES (
            'Administrador', 
            'admin@admin.com', 
            '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdOIGGrNG5f8X9VG', 
            'ADMIN', 
            true,
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
        );
        
        RAISE NOTICE 'Usuário admin criado com sucesso!';
    ELSE
        RAISE NOTICE 'Usuário admin já existe no banco de dados.';
    END IF;
END $$;

-- Verificar o usuário criado
SELECT 
    id, 
    nome, 
    email, 
    role, 
    ativo, 
    created_at 
FROM usuarios 
WHERE email = 'admin@admin.com';

-- Verificar todos os eventos existentes
SELECT 
    COUNT(*) as total_eventos,
    'eventos cadastrados' as info
FROM eventos;

SELECT 
    id,
    titulo,
    data,
    hora,
    organizador
FROM eventos 
ORDER BY data ASC
LIMIT 10;

COMMIT;