package com.shopping.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//better to keep model unexposed, good practice to separate model entity and dto
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
