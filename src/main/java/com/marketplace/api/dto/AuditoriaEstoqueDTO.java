package com.marketplace.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class AuditoriaEstoqueDTO {

    private Long produtoId;
    private String nomeProduto;
    private Integer saldoAtual;
    private List<String> movimentacoes;
}