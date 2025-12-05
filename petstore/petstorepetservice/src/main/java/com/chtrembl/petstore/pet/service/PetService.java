package com.chtrembl.petstore.pet.service;

import com.chtrembl.petstore.pet.entity.Pet;
import com.chtrembl.petstore.pet.mapper.EntityToModelMapper;
import com.chtrembl.petstore.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final EntityToModelMapper entityToModelMapper;
    @Transactional(readOnly = true)
    public List<com.chtrembl.petstore.pet.model.Pet> findPetsByStatus(List<String> status) {
        log.info("Finding pets with status: {}", status);
        List<Pet> pets = petRepository.findByStatusIn(status);
        return entityToModelMapper.toModelPetList(pets);
    }
    @Transactional(readOnly = true)
    public Optional<com.chtrembl.petstore.pet.model.Pet> findPetById(Long petId) {
        log.info("Finding pet with id: {}", petId);
        Optional<Pet> petOptional = petRepository.findById(petId);
        return Optional.of(entityToModelMapper.toModelPet(petOptional.orElse(null)));
    }
    @Transactional(readOnly = true)
    public List<com.chtrembl.petstore.pet.model.Pet> getAllPets() {
        log.info("Getting all pets");
        List<Pet> pets = petRepository.findAll();
        return entityToModelMapper.toModelPetList(pets);
    }
    @Transactional(readOnly = true)
    public long getPetCount() {
       return petRepository.count();
    }
}