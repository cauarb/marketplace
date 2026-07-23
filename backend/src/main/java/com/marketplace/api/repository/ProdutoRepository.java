package com.marketplace.api.repository;


import com.marketplace.api.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProdutoRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Produto> rowMapper = (rs, rowNum) -> {
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

    public  void salvar( Produto produto) {
        String sql = """
            INSERT INTO produtos (nome, descricao, preco, estoque, estoque_min, desconto)
                VALUES(?,?,?,?,?,?)
            """;

        jdbc.update(sql,
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getEstoqueMin(),
                produto.getDesconto() != null ? produto.getDesconto() : BigDecimal.ZERO
        );
    }

    public List<Produto> listarTodos(){
        String sql = "SELECT * FROM produtos";
        return jdbc.query(sql, rowMapper);
    }

    public Optional<Produto> buscarPorId(Long id){
        String sql = "SELECT * FROM produtos WHERE id = ?";
        List<Produto> resultado = jdbc.query(sql, rowMapper, id);
        return resultado.stream().findFirst();
    }


    public void atualizar( Produto produto) {
        String sql = """
            UPDATE produtos
                SET nome = ?, descricao = ?, preco = ?, estoque = ?, estoque_min = ?, desconto = ?
                WHERE id = ?
                """;

        jdbc.update(sql,
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getEstoqueMin(),
                produto.getDesconto() != null ? produto.getDesconto() : BigDecimal.ZERO,
                produto.getId()
        );
    }

    public void deletar(Long id) {
        jdbc.update("DELETE FROM produtos WHERE id = ?", id);
    }

    public Optional<Produto> buscaPorIdLock(Long id) {
        String sql = "SELECT * FROM produtos WHERE id = ? FOR UPDATE";
        List<Produto> resultado = jdbc.query(sql, rowMapper, id);
        return resultado.stream().findFirst();
    }

    public boolean existePorNome(String nome) {
        String sql = "SELECT COUNT(*) FROM produtos WHERE nome = ?";
        Integer total = jdbc.queryForObject(sql, Integer.class, nome);
        return total != null && total > 0;
    }

    public boolean temPedidosVinculados(Long produtoId) {
        String sql = "SELECT COUNT(*) FROM itens_pedido WHERE produto_id = ?";
        Integer total = jdbc.queryForObject(sql, Integer.class, produtoId);
        return total != null && total > 0;
    }
}