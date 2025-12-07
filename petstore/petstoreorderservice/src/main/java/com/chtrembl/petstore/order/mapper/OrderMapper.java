package com.chtrembl.petstore.order.mapper;

import com.chtrembl.petstore.order.model.Order;
import com.chtrembl.petstore.order.model.Product;
import com.chtrembl.petstore.order.entity.OrderEntity;
import com.chtrembl.petstore.order.entity.ProductEntity;

import java.util.stream.Collectors;

public class OrderMapper {

    private OrderMapper(){

    }

    public static OrderEntity toEntity(Order model) {
        if (model == null) return null;
        return OrderEntity.builder()
                .id(model.getId())
                .email(model.getEmail())
                .products(model.getProducts() != null
                        ? model.getProducts().stream().map(OrderMapper::toEntity).collect(Collectors.toList())
                        : null)
                .status(model.getStatus() != null
                        ? OrderEntity.Status.fromValue(model.getStatus().toString())
                        : null)
                .complete(model.getComplete())
                .build();
    }

    public static ProductEntity toEntity(Product model) {
        if (model == null) return null;
        return ProductEntity.builder()
                .id(model.getId())
                .quantity(model.getQuantity())
                .name(model.getName())
                .photoURL(model.getPhotoURL())
                .build();
    }

    // (Optional) Reverse mapping: Entity to Model
    public static Order toModel(OrderEntity entity) {
        if (entity == null) return null;
        return Order.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .products(entity.getProducts() != null
                        ? entity.getProducts().stream().map(OrderMapper::toModel).collect(Collectors.toList())
                        : null)
                .status(entity.getStatus() != null
                        ? Order.Status.fromValue(entity.getStatus().toString())
                        : null)
                .complete(entity.getComplete())
                .build();
    }

    public static Product toModel(ProductEntity entity) {
        if (entity == null) return null;
        return Product.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .name(entity.getName())
                .photoURL(entity.getPhotoURL())
                .build();
    }
}