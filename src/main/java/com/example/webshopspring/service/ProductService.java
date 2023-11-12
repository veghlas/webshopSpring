package com.example.webshopspring.service;

import com.example.webshopspring.domain.Order;
import com.example.webshopspring.domain.Product;
import com.example.webshopspring.domain.ProductType;
import com.example.webshopspring.dto.ProductCreateCommand;
import com.example.webshopspring.dto.ProductInfo;
import com.example.webshopspring.dto.ProductUpdateCommand;
import com.example.webshopspring.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private ProductRepository productRepository;

    private OrderService orderService;
    private ModelMapper modelMapper;
    @Autowired
    public ProductService(ProductRepository productRepository, OrderService orderService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    public ProductInfo saveProduct(ProductCreateCommand command) {
        Product product = modelMapper.map(command, Product.class);
        Order orderById = orderService.findById(command.getOrderId());
        product.setOrder(orderById);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductInfo.class);
    }

    public List<ProductInfo> listProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductInfo.class))
                .collect(Collectors.toList());
    }

    public ProductInfo getProductById(Long id) {
        return modelMapper.map(findById(id), ProductInfo.class);
    }

    private Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException();
        }
        return product.get();
    }

    public ProductInfo updateProductById(Long id, ProductUpdateCommand command) {
        Product product = findById(id);
        modelMapper.map(command, product);
        return modelMapper.map(product, ProductInfo.class);
    }


    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public List<ProductInfo> listProductsByType(ProductType productType) {
        List<Product> productList = productRepository.findByproductType(productType);
        return productList.stream()
                .map(product -> modelMapper.map(product, ProductInfo.class))
                .collect(Collectors.toList());
    }
}
