package com.marketplace.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovimentacaoDTO {

    @NotNull(message = "O produto é obrigatório")
    private Long produtoId;

    @NotBlank(message = "O tipo é obrigatório")
    private String tipo;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;

    private String motivo;
}