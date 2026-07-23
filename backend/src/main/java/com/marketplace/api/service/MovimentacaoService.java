package com.marketplace.api.service;

import com.marketplace.api.dto.MovimentacaoDTO;
import com.marketplace.api.model.MovimentacaoEstoque;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.MovimentacaoRepository;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public void registrar(MovimentacaoDTO dto) {


        Produto produto = produtoRepository.buscarPorId(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


        int novoEstoque = produto.getEstoque() + dto.getQuantidade();


        if (novoEstoque < produto.getEstoqueMin()) {
            throw new IllegalArgumentException(
                    "Movimentação deixaria o estoque abaixo do mínimo permitido (" +
                            produto.getEstoqueMin() + ")"
            );
        }


        if (novoEstoque < 0) {
            throw new IllegalArgumentException(
                    "Movimentação deixaria o estoque negativo"
            );
        }


        produto.setEstoque(novoEstoque);
        produtoRepository.atualizar(produto);


        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProdutoId(dto.getProdutoId());
        movimentacao.setTipo(dto.getTipo());
        movimentacao.setQuantidade(dto.getQuantidade());
        movimentacao.setMotivo(dto.getMotivo());
        movimentacaoRepository.salvar(movimentacao);
    }

    public List<MovimentacaoEstoque> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.listarPorProduto(produtoId);
    }

    public List<MovimentacaoEstoque> listarTodas() {
        return movimentacaoRepository.listarTodas();
    }
}