package com.marketplace.api.service;

import com.marketplace.api.dto.AuditoriaEstoqueDTO;
import com.marketplace.api.model.MovimentacaoEstoque;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.MovimentacaoRepository;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public List<AuditoriaEstoqueDTO> auditarTodos() {
        List<Produto> produtos = produtoRepository.listarTodos();
        List<AuditoriaEstoqueDTO> resultado = new ArrayList<>();

        for (Produto produto : produtos) {
            resultado.add(auditarProduto(produto));
        }

        return resultado;
    }

    public AuditoriaEstoqueDTO auditarPorId(Long produtoId) {
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return auditarProduto(produto);
    }

    private AuditoriaEstoqueDTO auditarProduto(Produto produto) {


        List<MovimentacaoEstoque> movimentacoes = movimentacaoRepository
                .listarPorProduto(produto.getId());


        List<String> extrato = new ArrayList<>();
        for (MovimentacaoEstoque mov : movimentacoes) {
            String linha = mov.getCriadoEm() + " | "
                    + mov.getTipo() + " | "
                    + (mov.getQuantidade() > 0 ? "+" : "")
                    + mov.getQuantidade()
                    + (mov.getMotivo() != null ? " | " + mov.getMotivo() : "");
            extrato.add(linha);
        }

        AuditoriaEstoqueDTO dto = new AuditoriaEstoqueDTO();
        dto.setProdutoId(produto.getId());
        dto.setNomeProduto(produto.getNome());
        dto.setSaldoAtual(produto.getEstoque());
        dto.setMovimentacoes(extrato);

        return dto;
    }
}