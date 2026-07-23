package com.marketplace.api.service;

import com.marketplace.api.dto.ItemPedidoDTO;
import com.marketplace.api.dto.PedidoDTO;
import com.marketplace.api.model.ItemPedido;
import com.marketplace.api.model.MovimentacaoEstoque;
import com.marketplace.api.model.Pedido;
import com.marketplace.api.model.Produto;
import com.marketplace.api.repository.MovimentacaoRepository;
import com.marketplace.api.repository.PedidoRepository;
import com.marketplace.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

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


            BigDecimal preco = produto.getPreco();
            BigDecimal desconto = produto.getDesconto() != null
                    ? produto.getDesconto()
                    : BigDecimal.ZERO;

            BigDecimal precoComDesconto = preco.subtract(
                    preco.multiply(desconto)
                            .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP)
            );

            ItemPedido item = new ItemPedido();
            item.setPedidoId(pedidoId);
            item.setProdutoId(produto.getId());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnit(precoComDesconto);
            pedidoRepository.salvarItem(item);

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.atualizar(produto);

            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setProdutoId(produto.getId());
            mov.setTipo("VENDA");
            mov.setQuantidade(-itemDTO.getQuantidade());
            mov.setMotivo("Pedido #" + pedidoId);
            movimentacaoRepository.salvar(mov);
        }

        return pedidoId;
    }

    public Pedido buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        List<ItemPedido> itens = pedidoRepository.buscarItensPorPedido(id);
        pedido.setItens(itens);

        BigDecimal total = itens.stream()
                .map(item -> item.getPrecoUnit()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(total);

        return pedido;
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = pedidoRepository.listarTodos();

        for (Pedido pedido : pedidos) {
            List<ItemPedido> itens = pedidoRepository.buscarItensPorPedido(pedido.getId());
            pedido.setItens(itens);

            BigDecimal total = itens.stream()
                    .map(item -> item.getPrecoUnit()
                            .multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            pedido.setValorTotal(total);
        }

        return pedidos;
    }

    @Transactional
    public void cancelar(Long id) {

        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (pedido.getStatus().equals("CANCELADO")) {
            throw new IllegalArgumentException("Pedido já está cancelado");
        }

        List<ItemPedido> itens = pedidoRepository.buscarItensPorPedidoId(id);

        for (ItemPedido item : itens) {
            Produto produto = produtoRepository.buscarPorId(item.getProdutoId())
                    .orElseThrow(() -> new RuntimeException(
                            "Produto não encontrado: " + item.getProdutoId()
                    ));

            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            produtoRepository.atualizar(produto);


            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setProdutoId(item.getProdutoId());
            mov.setTipo("CANCELAMENTO");
            mov.setQuantidade(item.getQuantidade());
            mov.setMotivo("Cancelamento do Pedido #" + id);
            movimentacaoRepository.salvar(mov);
        }

        pedidoRepository.atualizarStatus(id, "CANCELADO");
    }

    @Transactional
    public void  confirmar(Long id) {

        Pedido pedido = pedidoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getStatus().equals("PENDENTE")) {
            throw new IllegalArgumentException("" +
                    "Apenas pedidos pendentes podem ser confirmados: Status atual" +pedido.getStatus()
            );
        }

        pedidoRepository.atualizarStatus(id, "CONFIRMADO");
    }

}