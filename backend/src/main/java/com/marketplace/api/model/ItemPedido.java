package com.marketplace.api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemPedido {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnit;
}
