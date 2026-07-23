package com.marketplace.api.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private Integer estoqueMin;
    private BigDecimal desconto;
    private LocalDateTime criadoEm;
}
