INSERT INTO mesas (id, nome, status) VALUES (1, 'Mesa 1', 'LIVRE');
INSERT INTO mesas (id, nome, status) VALUES (2, 'Varanda', 'LIVRE');
INSERT INTO mesas (id, nome, status) VALUES (3, 'Balcão', 'LIVRE');

INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (1, 'Cerveja Lata', 'BEBIDA', 6.50, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (2, 'Refrigerante', 'BEBIDA', 5.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (3, 'Batata Frita', 'COMIDA', 18.00, true);

ALTER TABLE mesas ALTER COLUMN id RESTART WITH 4;
ALTER TABLE produtos ALTER COLUMN id RESTART WITH 4;

INSERT INTO usuario (id, email, senha, nome) VALUES (1, 'admin@seriguela.com', 'admin123', 'Administrador');
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 2;
