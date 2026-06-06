package com.marketplace.api.service;

import com.marketplace.api.dto.ItemPedidoDTO;
import com.marketplace.api.dto.PedidoDTO;
import com.marketplace.api.model.ItemPedido;
import com.marketplace.api.model.Pedido;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.PedidoRepository;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public Long criar(PedidoDTO dto) {

        Pedido pedido = new Pedido();
        pedido.setStatus("PENDENTE");
        Long pedidoId = pedidoRepository.salvar(pedido);

        for (ItemPedidoDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.buscarPorId(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Produto não encontrado: " + itemDTO.getProdutoId()
                    ));

            int estoqueDisponivel = produto.getEstoque() - produto.getEstoqueMin();

            if (itemDTO.getQuantidade() > estoqueDisponivel) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para: " + produto.getNome() +
                                ". Disponível: " + estoqueDisponivel
                );
            }

            ItemPedido item = new ItemPedido();
            item.setPedidoId(pedidoId);
            item.setProdutoId(produto.getId());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnit(produto.getPreco());
            pedidoRepository.salvarItem(item);


            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.atualizar(produto);
        }

        return pedidoId;
    }

    public Pedido buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));


        List<ItemPedido> itens = pedidoRepository.buscarItensPorPedido(id);
        pedido.setItens(itens);

        return pedido;
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.listarTodos();


        for (Pedido pedido : pedidos) {
            List<ItemPedido> itens = pedidoRepository.buscarItensPorPedido(pedido.getId());
            pedido.setItens(itens);
        }

        return pedidos;
    }
}