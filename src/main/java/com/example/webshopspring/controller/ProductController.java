package com.example.webshopspring.controller;

import com.example.webshopspring.domain.OrderInfo;
import com.example.webshopspring.domain.ProductType;
import com.example.webshopspring.dto.*;
import com.example.webshopspring.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductInfo> saveProduct(@Valid @RequestBody ProductCreateCommand command) {
        log.info("Http request POST / /api/product body: " + command.toString());
        ProductInfo productInfo = this.productService.saveProduct(command);
        return new ResponseEntity<>(productInfo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductInfo>> listAllOrders() {
        log.info("Http request GET / /api/product");
        List<ProductInfo> productInfoList = productService.listProducts();
        return new ResponseEntity<>(productInfoList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInfo> listOrderById(@PathVariable("productId") Long id) {
        log.info("Http request GET / /api/product/{productId} body: " + id);
        ProductInfo productInfo = productService.getProductById(id);
        return new ResponseEntity<>(productInfo, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductInfo> updateById(@PathVariable("productId") Long id,
                                                  @RequestBody @Valid ProductUpdateCommand command) {
        log.info("Http request PUT / /api/product/{productId} body: " + command.toString() + "with variable" + id);
        ProductInfo productInfo = productService.updateProductById(id, command);
        return new ResponseEntity<>(productInfo, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteById(@PathVariable("productId") Long id) {
        log.info("Http request DELETE / /api/product/{orderId} with variable: " + id);
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/type")
    public ResponseEntity<List<ProductInfo>> listBytype(@RequestParam(value = "type", required = false) ProductType productType) {
        log.info("Http request GET / /api/product/type with parameter genre: " + productType);
        List<ProductInfo> productInfoList = productService.listProductsByType(productType);
        return new ResponseEntity<>(productInfoList, HttpStatus.OK);
    }
}