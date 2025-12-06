package com.chtrembl.petstore.pet.repository;

import com.chtrembl.petstore.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    // Custom query methods if needed

    List<Pet> findByStatusIn(List<String> statuses);
}
