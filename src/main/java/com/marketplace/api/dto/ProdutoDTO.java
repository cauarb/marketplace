package com.marketplace.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no maximo 100 caractere")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O estoque não pode ser negativo")
    private Integer estoque;

    @NotNull(message = "O estoque minimo é obrigatório")
    @Min(value = 0, message = "O estoque mínimo não pode ser negativo")
    private Integer estoqueMin;

}
