package com.chtrembl.petstore.product.repository;

import com.chtrembl.petstore.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods if needed
}
