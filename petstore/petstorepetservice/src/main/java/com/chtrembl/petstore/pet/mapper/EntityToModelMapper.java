package com.chtrembl.petstore.pet.mapper;

import com.chtrembl.petstore.pet.model.Category;
import com.chtrembl.petstore.pet.model.Pet;
import com.chtrembl.petstore.pet.model.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
@Component
public class EntityToModelMapper {


    public List<Pet> toModelPetList(List<com.chtrembl.petstore.pet.entity.Pet> pets){
        if(null!=pets && !pets.isEmpty()){
          List<Pet> modelPets = new ArrayList<>();
          for(com.chtrembl.petstore.pet.entity.Pet pet : pets){
              modelPets.add(toModelPet(pet));
          }
          return  modelPets;
        }
        return Collections.emptyList();


    }

    public Pet toModelPet(com.chtrembl.petstore.pet.entity.Pet pet){
        if(null!=pet){
            Pet modelPet = new Pet();
            modelPet.setId(pet.getId());
            modelPet.setName(pet.getName());
            modelPet.setStatus(Pet.Status.fromValue(pet.getStatus()));
            modelPet.setPhotoURL(pet.getPhotoURL());
            modelPet.setCategory(toModelPetCategory(pet.getCategory()));
            modelPet.setTags(toModelPetTag(pet.getTags()));
            return modelPet;
        }
        return new Pet();
    }

    private Category toModelPetCategory(com.chtrembl.petstore.pet.entity.Category category){
        if(null!=category){
            Category modelCategory = new Category();
            modelCategory.setId(category.getId());
            modelCategory.setName(category.getName());
            return modelCategory;
        }
        return new Category();
    }

    private List<Tag> toModelPetTag(Set<com.chtrembl.petstore.pet.entity.Tag> tags){
        if(null!=tags){
            List<Tag> modelPetTags = new ArrayList<>();
            for(com.chtrembl.petstore.pet.entity.Tag tag : tags){
                Tag modelPetTag = new Tag();
                modelPetTag.setId(tag.getId());
                modelPetTag.setName(tag.getName());
                modelPetTags.add(modelPetTag);
            }
            return  modelPetTags;
        }

        return Collections.emptyList();
    }






}
