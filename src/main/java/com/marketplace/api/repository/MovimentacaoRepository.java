package com.marketplace.api.repository;

import com.marketplace.api.model.MovimentacaoEstoque;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovimentacaoRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<MovimentacaoEstoque> rowMapper = (rs, rowNum) -> {
        MovimentacaoEstoque m = new MovimentacaoEstoque();
        m.setId(rs.getLong("id"));
        m.setProdutoId(rs.getLong("produto_id"));
        m.setTipo(rs.getString("tipo"));
        m.setQuantidade(rs.getInt("quantidade"));
        m.setMotivo(rs.getString("motivo"));
        m.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        return m;
    };

    public void salvar(MovimentacaoEstoque movimentacao) {
        String sql = """
                INSERT INTO movimentacao_estoque (produto_id, tipo, quantidade, motivo)
                VALUES (?, ?, ?, ?)
                """;
        jdbc.update(sql,
                movimentacao.getProdutoId(),
                movimentacao.getTipo(),
                movimentacao.getQuantidade(),
                movimentacao.getMotivo()
        );
    }

    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        String sql = """
                SELECT * FROM movimentacao_estoque
                WHERE produto_id = ?
                ORDER BY criado_em DESC
                """;
        return jdbc.query(sql, rowMapper, produtoId);
    }

    public List<MovimentacaoEstoque> listarTodas() {
        String sql = "SELECT * FROM movimentacao_estoque ORDER BY criado_em DESC";
        return jdbc.query(sql, rowMapper);
    }
}