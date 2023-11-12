package com.example.webshopspring.dto;

import com.example.webshopspring.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateCommand {
    private String name;
    private ProductType productType;
    private Integer price;
    private Long orderId;
}
