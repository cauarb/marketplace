package com.marketplace.api.service;

import com.marketplace.api.dto.PainelDTO;
import com.marketplace.api.repository.PainelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PainelService {

    private final PainelRepository repository;

    public PainelDTO gerarPainel() {

        PainelDTO painel = new PainelDTO();

        painel.setPedidosHoje(repository.contarPedidosHoje());
        painel.setPedidosSemana(repository.contarPedidosSemana());
        painel.setPedidosMes(repository.contarPedidosMes());

        painel.setFaturamentoHoje(repository.faturamentoHoje());
        painel.setFaturamentoSemana(repository.faturamentoSemana());
        painel.setFaturamentoMes(repository.faturamentoMes());

        painel.setPedidosPendentes(repository.contarPorStatus("PENDENTE"));
        painel.setPedidosConfirmados(repository.contarPorStatus("CONFIRMADO"));
        painel.setPedidosCancelados(repository.contarPorStatus("CANCELADO"));


        painel.setTotalProdutos(repository.totalProdutos());
        painel.setValorTotalEstoque(repository.valorTotalEstoque());
        painel.setProdutoMaisvendido(repository.produtoMaisVendido());

        painel.setProdutosEmAlerta(repository.produtosEmAlerta());

        return painel;


    }

}
