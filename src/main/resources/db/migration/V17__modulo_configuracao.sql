-- Dados da Escola (Singleton)
CREATE TABLE config_escola (
    id UUID PRIMARY KEY,
    nome_instituicao VARCHAR(255) NOT NULL,
    cnpj VARCHAR(20),
    inep VARCHAR(20),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    cep VARCHAR(10),
    telefone VARCHAR(20),
    email_contato VARCHAR(100),
    logotipo_base64 TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Equipe Gestora e Administrativa
CREATE TABLE config_equipe (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    email VARCHAR(100),
    cargo VARCHAR(50) NOT NULL, -- DIRETOR, SECRETARIO, COORDENADOR, MONITOR, OUTRO
    ativo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Parametrização Financeira por Curso e Ano Letivo
CREATE TABLE config_valor_curso (
    id UUID PRIMARY KEY,
    curso_id UUID NOT NULL,
    ano_letivo_id UUID NOT NULL,
    valor_matricula DECIMAL(10,2) NOT NULL DEFAULT 0,
    valor_mensalidade DECIMAL(10,2) NOT NULL DEFAULT 0,
    quantidade_meses INTEGER NOT NULL DEFAULT 12,
    dia_vencimento_padrao INTEGER DEFAULT 10,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_valor_curso FOREIGN KEY (curso_id) REFERENCES curso(id),
    CONSTRAINT fk_valor_ano_letivo FOREIGN KEY (ano_letivo_id) REFERENCES ano_letivo(id),
    CONSTRAINT uk_curso_ano UNIQUE (curso_id, ano_letivo_id)
);

-- Inserir registro inicial da escola (Singleton)
INSERT INTO config_escola (id, nome_instituicao) 
VALUES ('00000000-0000-0000-0000-000000000001', 'Minha Escola Modelo');
