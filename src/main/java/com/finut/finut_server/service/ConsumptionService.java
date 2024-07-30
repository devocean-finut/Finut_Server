package com.finut.finut_server.service;

import com.finut.finut_server.domain.product.Product;
import com.finut.finut_server.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionService {
    private static ProductRepository productRepository;


    @Autowired
    public ConsumptionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public static Optional<Product> getProductInfo(Long productId) {
        return productRepository.findById(productId);
    }
}
