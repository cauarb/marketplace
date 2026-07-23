package com.marketplace.api.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovimentacaoEstoque {

    private Long id;
    private Long produtoId;
    private String tipo;
    private Integer quantidade;
    private String motivo;
    private LocalDateTime criadoEm;
}
