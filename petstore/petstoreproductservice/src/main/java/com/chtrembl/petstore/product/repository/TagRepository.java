package com.chtrembl.petstore.product.repository;

import com.chtrembl.petstore.product.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // Custom query methods if needed
}