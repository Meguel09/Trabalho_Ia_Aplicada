INSERT INTO mesas (id, numero, status) VALUES (1, 1, 'LIVRE');
INSERT INTO mesas (id, numero, status) VALUES (2, 2, 'LIVRE');
INSERT INTO mesas (id, numero, status) VALUES (3, 3, 'LIVRE');

INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (1, 'Cerveja Lata', 'BEBIDA', 6.50, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (2, 'Refrigerante', 'BEBIDA', 5.00, true);
INSERT INTO produtos (id, nome, categoria, preco, ativo) VALUES (3, 'Batata Frita', 'COMIDA', 18.00, true);

ALTER TABLE mesas ALTER COLUMN id RESTART WITH 4;
ALTER TABLE produtos ALTER COLUMN id RESTART WITH 4;