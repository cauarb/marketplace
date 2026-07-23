package com.marketplace.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Pedido {

    private Long id;
    private LocalDateTime criadoEm;
    private String status;
    private BigDecimal valorTotal;
    private List<ItemPedido> itens;

}
