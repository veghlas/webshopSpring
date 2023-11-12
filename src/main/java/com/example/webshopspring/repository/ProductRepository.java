package com.example.webshopspring.repository;

import com.example.webshopspring.domain.Product;
import com.example.webshopspring.domain.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.productType=:productType")
    List<Product> findByproductType(ProductType productType);
}
