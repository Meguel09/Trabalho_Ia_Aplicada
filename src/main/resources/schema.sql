CREATE TABLE mesas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'LIVRE'
);

CREATE TABLE contas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mesa_id BIGINT NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    individual BOOLEAN NOT NULL DEFAULT TRUE,
    taxa_servico BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTA',
    aberta_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fechada_em TIMESTAMP NULL,

    CONSTRAINT fk_conta_mesa
        FOREIGN KEY (mesa_id)
        REFERENCES mesas(id)
);

CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_produto_preco
        CHECK (preco > 0)
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pedido_conta
        FOREIGN KEY (conta_id)
        REFERENCES contas(id)
);

CREATE TABLE itens_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id),

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),

    CONSTRAINT chk_item_quantidade
        CHECK (quantidade > 0),

    CONSTRAINT chk_item_preco
        CHECK (preco_unitario > 0)
);

CREATE TABLE pagamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    forma VARCHAR(50) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    pago_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pagamento_conta
        FOREIGN KEY (conta_id)
        REFERENCES contas(id),

    CONSTRAINT chk_pagamento_valor
        CHECK (valor > 0)
);

CREATE TABLE impressoes_conta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    conteudo TEXT NOT NULL,
    impressa_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_impressao_conta
        FOREIGN KEY (conta_id)
        REFERENCES contas(id)
);