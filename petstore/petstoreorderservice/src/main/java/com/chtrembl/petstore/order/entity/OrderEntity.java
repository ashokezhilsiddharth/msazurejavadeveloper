package com.chtrembl.petstore.order.entity;


import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Container(containerName = "order")
public class OrderEntity {

    @Id
    @PartitionKey
    @EqualsAndHashCode.Include
    private String id;


    private String email;

    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();

    private Status status;

    @Builder.Default
    private Boolean complete = false;

    public Boolean getComplete() {
        return complete != null ? complete : false;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete != null ? complete : false;
    }

    public List<ProductEntity> getProducts() {
        return products != null ? products : new ArrayList<>();
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products != null ? products : new ArrayList<>();
    }

    public enum Status {
        PLACED("placed"),
        APPROVED("approved"),
        DELIVERED("delivered");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static Status fromValue(String text) {
            if (text == null) {
                return null;
            }
            for (Status status : Status.values()) {
                if (String.valueOf(status.value).equalsIgnoreCase(text.trim())) {
                    return status;
                }
            }
            return null;
        }
    }
}
