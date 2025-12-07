package com.chtrembl.petstore.order.repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.chtrembl.petstore.order.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CosmosRepository<OrderEntity, String> {
    // Custom query methods if needed
}
