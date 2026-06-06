package com.marketplace.api.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Pedido {

    private Long id;
    private LocalDateTime criadoEm;
    private String status;
    private List<ItemPedido> itens;
}
