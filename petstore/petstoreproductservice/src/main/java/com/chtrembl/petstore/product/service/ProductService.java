package com.chtrembl.petstore.product.service;


import com.chtrembl.petstore.product.entity.Product;
import com.chtrembl.petstore.product.mapper.EntityToModelMapper;
import com.chtrembl.petstore.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityToModelMapper entityToModelMapper;

    @Transactional(readOnly = true)
    public List<com.chtrembl.petstore.product.model.Product> findProductsByStatus(List<String> status) {
        log.info("Finding products with status: {}", status);
        List<Product> products = productRepository.findByStatusIn(status);
        return  entityToModelMapper.toModelProductList(products);
    }
    @Transactional(readOnly = true)
    public Optional<com.chtrembl.petstore.product.model.Product> findProductById(Long productId) {
        log.info("Finding product with id: {}", productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        return Optional.of(entityToModelMapper.toModelProduct(productOptional.orElse(null)));
    }
    @Transactional(readOnly = true)
    public List<com.chtrembl.petstore.product.model.Product> getAllProducts() {
        log.info("Getting all products");
        List<Product> products = productRepository.findAll();
        return entityToModelMapper.toModelProductList(products);
    }
    @Transactional(readOnly = true)
    public long getProductCount() {
        return productRepository.count();
    }
}