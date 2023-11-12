package com.example.webshopspring.domain;

import com.example.webshopspring.dto.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    private String customerName;
    private List<ProductInfo> productList;
}
