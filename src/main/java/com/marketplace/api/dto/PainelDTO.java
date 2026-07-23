package com.marketplace.api.dto;

import com.marketplace.api.model.Produto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PainelDTO {

    private Integer pedidosHoje;
    private Integer pedidosSemana;
    private Integer pedidosMes;

    private BigDecimal faturamentoHoje;
    private BigDecimal faturamentoSemana;
    private BigDecimal faturamentoMes;

    private Integer pedidosPendentes;
    private Integer pedidosConfirmados;
    private Integer pedidosCancelados;

    private Integer totalProdutos;
    private BigDecimal valorTotalEstoque;
    private String produtoMaisvendido;

    private List<Produto> produtosEmAlerta;
}
