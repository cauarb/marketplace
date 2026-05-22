package com.marketplace.api.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    private List<ItemPedidoDTO> itens;
}
