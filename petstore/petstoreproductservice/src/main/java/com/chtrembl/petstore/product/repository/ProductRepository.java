package com.chtrembl.petstore.product.repository;
import com.chtrembl.petstore.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods if needed
    List<Product> findByStatusIn(List<String> statuses);
}