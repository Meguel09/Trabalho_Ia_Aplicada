INSERT INTO mesas (id, nome, status) VALUES (1, 'Mesa 1', 'OCUPADA');
INSERT INTO mesas (id, nome, status) VALUES (2, 'Varanda', 'OCUPADA');
INSERT INTO mesas (id, nome, status) VALUES (3, 'Balcao', 'FECHADA');
INSERT INTO mesas (id, nome, status) VALUES (4, 'Mesa 4', 'LIVRE');
INSERT INTO mesas (id, nome, status) VALUES (5, 'Deck', 'OCUPADA');
INSERT INTO mesas (id, nome, status) VALUES (6, 'Jardim', 'LIVRE');

INSERT INTO categorias (nome) VALUES ('Cervejas');
INSERT INTO categorias (nome) VALUES ('Refrigerantes');
INSERT INTO categorias (nome) VALUES ('Petiscos');
INSERT INTO categorias (nome) VALUES ('Drinks');
INSERT INTO categorias (nome) VALUES ('Doses');

INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (1, 'Cerveja Lata', 'Cervejas', 6.50, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (2, 'Refrigerante', 'Refrigerantes', 5.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (3, 'Batata Frita', 'Petiscos', 18.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (4, 'Caipirinha', 'Drinks', 16.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (5, 'Calabresa Acebolada', 'Petiscos', 28.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (6, 'Dose de Cachaca', 'Doses', 8.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (7, 'Agua Mineral', 'Refrigerantes', 4.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (8, 'Gin Tonica', 'Drinks', 24.00, true);

INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (1, 1, 'Ana Paula', true, 'ABERTA', true, '2026-05-13 18:10:00', null);
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (2, 1, 'Bruno', true, 'ABERTA', false, '2026-05-13 18:18:00', null);
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (3, 2, 'Carla', true, 'ABERTA', true, '2026-05-13 18:35:00', null);
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (4, 2, 'Diego', true, 'FECHADA', false, '2026-05-13 17:20:00', '2026-05-13 19:05:00');
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (5, 3, 'Elisa', true, 'FECHADA', true, '2026-05-13 16:40:00', '2026-05-13 18:15:00');
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (6, 5, 'Felipe', true, 'ABERTA', false, '2026-05-13 19:00:00', null);
INSERT INTO contas (id, mesa_id, cliente, individual, status, taxa_servico, aberta_em, fechada_em)
VALUES (7, 5, 'Marina', true, 'ABERTA', true, '2026-05-13 19:12:00', null);

INSERT INTO pedidos (id, conta_id, criado_em) VALUES (1, 1, '2026-05-13 18:12:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (2, 1, '2026-05-13 18:45:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (3, 2, '2026-05-13 18:22:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (4, 3, '2026-05-13 18:38:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (5, 4, '2026-05-13 17:25:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (6, 5, '2026-05-13 16:48:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (7, 6, '2026-05-13 19:03:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (8, 7, '2026-05-13 19:15:00');
INSERT INTO pedidos (id, conta_id, criado_em) VALUES (9, 7, '2026-05-13 19:35:00');

INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (1, 1, 1, 3, 6.50);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (2, 1, 3, 1, 18.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (3, 2, 4, 2, 16.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (4, 3, 2, 2, 5.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (5, 3, 7, 1, 4.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (6, 4, 8, 1, 24.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (7, 4, 5, 1, 28.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (8, 5, 1, 4, 6.50);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (9, 5, 6, 2, 8.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (10, 6, 3, 2, 18.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (11, 6, 2, 3, 5.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (12, 7, 1, 5, 6.50);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (13, 7, 5, 1, 28.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (14, 8, 4, 1, 16.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (15, 8, 8, 1, 24.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (16, 9, 6, 3, 8.00);
INSERT INTO itens_pedido (id, pedido_id, produto_id, quantidade, preco_unitario) VALUES (17, 9, 7, 2, 4.00);

ALTER TABLE mesas ALTER COLUMN id RESTART WITH 7;
ALTER TABLE contas ALTER COLUMN id RESTART WITH 8;
ALTER TABLE pedidos ALTER COLUMN id RESTART WITH 10;
ALTER TABLE itens_pedido ALTER COLUMN id RESTART WITH 18;

INSERT INTO usuario (id, email, senha, nome) VALUES (1, 'admin@seriguela.com', 'admin123', 'Administrador');
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 2;
