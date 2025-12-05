package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods if needed
}
