package com.marketplace.api.repository;

import com.marketplace.api.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PainelRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Produto> produtoRowMapper = (rs, rowNum) -> {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setPreco(rs.getBigDecimal("preco"));
        p.setEstoque(rs.getInt("estoque"));
        p.setEstoqueMin(rs.getInt("estoque_min"));
        p.setDesconto(rs.getBigDecimal("desconto"));
        p.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        return p;
    };

    public Integer contarPedidosHoje() {
        String sql = """
                SELECT COUNT(*) FROM pedidos
                WHERE DATE(criado_em) = CURDATE()
                AND status <> 'CANCELADO'
                """;
        return jdbc.queryForObject(sql, Integer.class);
    }

    public Integer contarPedidosSemana() {
        String sql = """
                SELECT COUNT(*) FROM pedidos
                WHERE criado_em >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
                AND status <> 'CANCELADO'
                """;

        return jdbc.queryForObject(sql, Integer.class);
    }

    public Integer contarPedidosMes() {
        String sql = """
              SELECT COUNT(*) FROM pedidos
            WHERE criado_em >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
            AND status <> 'CANCELADO'
            """;

        return jdbc.queryForObject(sql, Integer.class);
    }

    public BigDecimal faturamentoHoje() {
        String sql = """
                SELECT COALESCE(SUM(i.quantidade * i.preco_unit), 0)
                FROM pedidos p
                JOIN itens_pedido i ON i.pedido_id = p.id
                WHERE DATE(p.criado_em) = CURDATE()
                AND p.status <> 'CANCELADO'
                """;
        return jdbc.queryForObject(sql, BigDecimal.class);
    }

    public BigDecimal faturamentoSemana() {
        String sql = """
                SELECT COALESCE(SUM(i.quantidade * i.preco_unit), 0)
                FROM pedidos p
                JOIN itens_pedido i ON i.pedido_id = p.id
                WHERE p.criado_em >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
                AND p.status <> 'CANCELADO'
                """;
        return jdbc.queryForObject(sql, BigDecimal.class);
    }

    public BigDecimal faturamentoMes() {
        String sql = """
                 SELECT COALESCE(SUM(i.quantidade * i.preco_unit), 0)
                 FROM pedidos p
                 JOIN itens_pedido i ON i.pedido_id = p.id
                 WHERE p.criado_em >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
                 AND p.status <> 'CANCELADO' 
                """;
        return jdbc.queryForObject(sql, BigDecimal.class);
    }

    public Integer contarPorStatus(String status) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE status = ?";
        return jdbc.queryForObject(sql, Integer.class, status);
    }

    public Integer totalProdutos() {
        String sql = "SELECT COUNT(*) FROM produtos";
        return jdbc.queryForObject(sql, Integer.class);
    }

    public BigDecimal valorTotalEstoque() {
        String sql = "SELECT COALESCE(SUM(preco * estoque), 0) FROM produtos";
        return jdbc.queryForObject(sql, BigDecimal.class);
    }

    public String produtoMaisVendido() {
        String sql = """
              SELECT pr.nome
              FROM itens_pedido i
              JOIN produtos pr ON pr.id = i.produto_id
              JOIN pedidos p ON p.id = i.pedido_id
              WHERE p.status <> 'CANCELADO'
              GROUP BY pr.id, pr.nome
              ORDER BY SUM(i.quantidade) DESC
              LIMIT 1
        """;
        List<String> resultado = jdbc.queryForList(sql, String.class);
        return resultado.isEmpty() ? "Nenhum produto vendido" : resultado.get(0);

    }

    public List<Produto> produtosEmAlerta() {
        String sql = """
                SELECT * FROM produtos
                WHERE estoque <= estoque_min * 1.15
                ORDER BY estoque ASC
                """;
        return jdbc.query(sql, produtoRowMapper);
    }
}
