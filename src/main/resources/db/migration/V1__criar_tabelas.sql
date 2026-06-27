CREATE TABLE IF NOT EXISTS produtos (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    descricao   TEXT,
    preco       DECIMAL(10, 2) NOT NULL,
    estoque     INT NOT NULL DEFAULT 0,
    estoque_min INT NOT NULL DEFAULT 5,
    criado_em   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE'
    );

CREATE TABLE IF NOT EXISTS itens_pedido (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id  INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unit DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id)  REFERENCES pedidos(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
    );