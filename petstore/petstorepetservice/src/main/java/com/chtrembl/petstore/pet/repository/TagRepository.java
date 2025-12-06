package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // Custom query methods if needed
}
