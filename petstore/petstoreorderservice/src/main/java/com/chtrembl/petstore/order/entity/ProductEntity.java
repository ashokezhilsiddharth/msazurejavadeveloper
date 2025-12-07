package com.chtrembl.petstore.order.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    private Long id;
    @Builder.Default
    private Integer quantity = 0;
    private String name;
    private String photoURL;

    public Integer getQuantity() {
        return quantity != null ? quantity : 0;
    }
}
