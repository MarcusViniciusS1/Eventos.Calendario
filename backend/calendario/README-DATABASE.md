# Configuração do Banco de Dados

## Problema Identificado

O erro ocorre porque o Hibernate está tentando fazer migrações automáticas em tabelas que já existem com estruturas incompatíveis. Os principais problemas são:

1. **Conversão de tipos**: Colunas `data` e `hora` não podem ser convertidas automaticamente
2. **Colunas NOT NULL**: Tentativa de adicionar colunas obrigatórias em tabelas com dados existentes
3. **Conflitos de schema**: Estrutura existente incompatível com o modelo JPA

## Solução

### 1. Resetar o Banco de Dados

Execute o script `database-reset.sql` no PostgreSQL:

```bash
# Conectar ao PostgreSQL
psql -U postgres -h localhost

# Executar o script
\i database-reset.sql
```

### 2. Configuração Atualizada

- Mudei `ddl-auto` para `create-drop` temporariamente
- Adicionei configuração de timezone
- Melhorei o tratamento de erros na inicialização

### 3. Estrutura das Tabelas

**Tabela usuarios:**
```sql
- id (BIGSERIAL PRIMARY KEY)
- nome (VARCHAR(100) NOT NULL)
- email (VARCHAR(150) NOT NULL UNIQUE)
- senha (VARCHAR(255) NOT NULL)
- role (VARCHAR(20) NOT NULL DEFAULT 'USER')
- ativo (BOOLEAN NOT NULL DEFAULT true)
- created_at (TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)
- updated_at (TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)
```

**Tabela eventos:**
```sql
- id (BIGSERIAL PRIMARY KEY)
- titulo (VARCHAR(200) NOT NULL)
- descricao (TEXT NOT NULL)
- data (DATE NOT NULL)
- hora (TIME NOT NULL)
- local (VARCHAR(300) NOT NULL)
- organizador (VARCHAR(150) NOT NULL)
- created_at (TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)
- updated_at (TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)
```

### 4. Dados Iniciais

O script cria automaticamente:
- Usuário admin: `admin@admin.com` / `admin123`
- 5 eventos de exemplo para teste

## Como Executar

1. **Parar a aplicação** se estiver rodando
2. **Executar o script SQL** para resetar o banco
3. **Iniciar a aplicação** novamente

```bash
# No diretório backend/calendario
mvn spring-boot:run
```

## Verificação

Após a inicialização, você deve ver:
- ✅ Usuário admin verificado
- ✅ Sistema inicializado com sucesso
- Logs sem erros de SQL

## Credenciais de Teste

- **Admin**: admin@admin.com / admin123
- **Endpoints**: 
  - Frontend: http://localhost:3000
  - Backend: http://localhost:8080
  - Health Check: http://localhost:8080/api/health