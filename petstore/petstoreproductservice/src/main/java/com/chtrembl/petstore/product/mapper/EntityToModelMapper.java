package com.chtrembl.petstore.product.mapper;


import com.chtrembl.petstore.product.model.Product;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class EntityToModelMapper {


    public List<Product> toModelProductList(List<com.chtrembl.petstore.product.entity.Product> products){
        if(null!=products && !products.isEmpty()){
          List<Product> modelPets = new ArrayList<>();
          for(com.chtrembl.petstore.product.entity.Product product : products){
              modelPets.add(toModelProduct(product));
          }
          return  modelPets;
        }
        return Collections.emptyList();


    }

    public Product toModelProduct(com.chtrembl.petstore.product.entity.Product product){
        if(null!=product){
            Product modelProduct = new Product();
            modelProduct.setId(product.getId());
            modelProduct.setName(product.getName());
            modelProduct.setStatus(Product.Status.fromValue(product.getStatus()));
            modelProduct.setPhotoURL(product.getPhotoURL());
            modelProduct.setCategory(toModelProductCategory(product.getCategory()));
            modelProduct.setTags(toModelProductTag(product.getTags()));
            return modelProduct;
        }
        return new Product();
    }

    private com.chtrembl.petstore.product.model.Category toModelProductCategory(com.chtrembl.petstore.product.entity.Category category){
        if(null!=category){
            com.chtrembl.petstore.product.model.Category modelCategory = new com.chtrembl.petstore.product.model.Category();
            modelCategory.setId(category.getId());
            modelCategory.setName(category.getName());
            return modelCategory;
        }
        return new com.chtrembl.petstore.product.model.Category();
    }

    private List<com.chtrembl.petstore.product.model.Tag> toModelProductTag(Set<com.chtrembl.petstore.product.entity.Tag> tags){
        if(null!=tags && !tags.isEmpty()){
            List<com.chtrembl.petstore.product.model.Tag> modelPetTags = new ArrayList<>();
            for(com.chtrembl.petstore.product.entity.Tag tag : tags){
                com.chtrembl.petstore.product.model.Tag modelPetTag = new com.chtrembl.petstore.product.model.Tag();
                modelPetTag.setId(tag.getId());
                modelPetTag.setName(tag.getName());
                modelPetTags.add(modelPetTag);
            }
            return  modelPetTags;
        }

        return Collections.emptyList();
    }






}
