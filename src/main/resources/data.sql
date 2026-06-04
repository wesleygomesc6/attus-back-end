-- Vendedores
INSERT INTO vendedores (nome) VALUES ('Carlos Souza');
INSERT INTO vendedores (nome) VALUES ('Fernanda Lima');
INSERT INTO vendedores (nome) VALUES ('Ricardo Oliveira');

-- Clientes
INSERT INTO clientes (nome, email) VALUES ('Empresa Alpha Ltda', 'contato@alpha.com.br');
INSERT INTO clientes (nome, email) VALUES ('Empresa Beta S.A.', 'contato@beta.com.br');

-- Pedidos
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id)
VALUES ('Pedido de equipamentos','Solicitação de 10 notebooks e 5 monitores para o setor de TI.','PENDENTE', 1, 1);
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id)
VALUES ('Contrato de manutenção','Renovação anual do contrato de manutenção preventiva.','EM_ANDAMENTO', 2, 2);
INSERT INTO pedidos (titulo, descricao, status, cliente_id, vendedor_id)
VALUES ('Licenças de software','Aquisição de 20 licenças do pacote Office para uso corporativo.','EM_ANALISE', 1, 3);