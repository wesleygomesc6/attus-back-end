-- Vendedores
INSERT INTO vendedores (nome) VALUES ('Carlos Souza');
INSERT INTO vendedores (nome) VALUES ('Fernanda Lima');
INSERT INTO vendedores (nome) VALUES ('Ricardo Oliveira');

-- Clientes
INSERT INTO clientes (nome, email) VALUES ('Empresa Alpha Ltda', 'contato@alpha.com.br');
INSERT INTO clientes (nome, email) VALUES ('Empresa Beta S.A.', 'contato@beta.com.br');

-- Pedidos
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id, criado_em)
VALUES ('Pedido de equipamentos','Solicitação de 10 notebooks e 5 monitores para o setor de TI.','PENDENTE', 1, 1, '2026-06-04T20:15:13.185Z');
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id, criado_em)
VALUES ('Cadeiras','12 cadeiras de escritório para a sala 232.','EM_ANDAMENTO', 2, 2, '2026-05-05T08:25:52.185Z');
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id, criado_em)
VALUES ('Licenças de software','Aquisição de 20 licenças do pacote Office para uso corporativo.','EM_ANALISE', 1, 3, '2026-06-05T13:56:13.185Z');