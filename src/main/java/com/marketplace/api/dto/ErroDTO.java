package com.marketplace.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroDTO {

    private int status;
    private String mensagem;
}