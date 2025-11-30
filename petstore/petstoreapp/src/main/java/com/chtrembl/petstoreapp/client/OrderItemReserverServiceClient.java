package com.chtrembl.petstoreapp.client;

import com.chtrembl.petstoreapp.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "order-item-reserver-service",
        contextId = "orderItemReserverService",
        url = "${petstore.service.itemReserver.url}",
        configuration = FeignConfig.class
)
public interface OrderItemReserverServiceClient {

    @PostMapping("/api/OrderItemsReserver")
    String reserveOrderItems(@RequestBody String reservedOrderJson);


}