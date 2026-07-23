package com.marketplace.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FakestoreDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String image;

}
