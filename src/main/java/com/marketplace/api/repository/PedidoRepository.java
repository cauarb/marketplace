package com.marketplace.api.repository;

import com.marketplace.api.model.ItemPedido;
import com.marketplace.api.model.Pedido;
import com.marketplace.api.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PedidoRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Pedido> pedidoRowMapper = (rs, rowNum) -> {
        Pedido p = new Pedido();
        p.setId(rs.getLong("id"));
        p.setStatus(rs.getString("status"));
        p.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        return p;
    };

    private final RowMapper<ItemPedido> itemRowMapper = (rs, rowNum) -> {
        ItemPedido i = new ItemPedido();
        i.setId(rs.getLong("id"));
        i.setPedidoId(rs.getLong("pedido_id"));
        i.setProdutoId(rs.getLong("produto_id"));
        i.setQuantidade(rs.getInt("quantidade"));
        i.setPrecoUnit(rs.getBigDecimal("preco_unit"));
        return i;
    };

    public Long salvar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (status) VALUES (?)";

        // KeyHolder captura o ID gerado automaticamente pelo banco
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pedido.getStatus());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void salvarItem(ItemPedido item) {
        String sql = """
                INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unit)
                VALUES (?, ?, ?, ?)
                """;

        jdbc.update(sql,
                item.getPedidoId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnit()
        );
    }

    public Optional<Pedido> buscarPorId(Long id) {
        String sql = "SELECT * FROM pedidos WHERE id = ?";
        List<Pedido> resultado = jdbc.query(sql, pedidoRowMapper, id);
        return resultado.stream().findFirst();
    }

    public List<ItemPedido> buscarItensPorPedido(Long pedidoId) {
        String sql = "SELECT * FROM itens_pedido WHERE pedido_id = ?";
        return jdbc.query(sql, itemRowMapper, pedidoId);
    }

    public List<Pedido> listarTodos() {
        String sql = "SELECT * FROM pedidos";
        return jdbc.query(sql, pedidoRowMapper);
    }

    public void atualizarStatus(Long id, String status) {
        String sql = "UPDATE pedidos SET status = ? WHERE id = ?";
        jdbc.update(sql, status, id);
    }

    public List<ItemPedido> buscarItensPorPedidoId(Long pedidoId) {
        String sql = "SELECT * FROM itens_pedido WHERE pedido_id = ?";
        return jdbc.query(sql, itemRowMapper, pedidoId);
    }
}