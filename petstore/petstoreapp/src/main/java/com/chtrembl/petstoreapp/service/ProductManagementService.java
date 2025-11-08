package com.chtrembl.petstoreapp.service;

import com.chtrembl.petstoreapp.client.ProductServiceClient;
import com.chtrembl.petstoreapp.exception.ProductServiceException;
import com.chtrembl.petstoreapp.model.ContainerEnvironment;
import com.chtrembl.petstoreapp.model.Product;
import com.chtrembl.petstoreapp.model.Tag;
import com.chtrembl.petstoreapp.model.User;
import com.chtrembl.petstoreapp.telemetry.PetStoreTelemetryClient;
import com.microsoft.applicationinsights.telemetry.SeverityLevel;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chtrembl.petstoreapp.config.Constants.CATEGORY;
import static com.chtrembl.petstoreapp.config.Constants.OPERATION;
import static com.chtrembl.petstoreapp.config.Constants.REQUEST_ID;
import static com.chtrembl.petstoreapp.config.Constants.TRACE_ID;
import static com.chtrembl.petstoreapp.model.Status.AVAILABLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductManagementService {

    private final User sessionUser;
    private final ContainerEnvironment containerEnvironment;
    private final ProductServiceClient productServiceClient;
    private final PetStoreTelemetryClient petStoreTelemetryClient;

    public Collection<Product> getProductsByCategory(String category, List<Tag> tags)  {


        try{
            // This line throws the exception
            throw new Exception("Cannot move further");
        }catch(Exception e){

            log.error("An expected test exception occurred: {}", e.getMessage(), e);


            petStoreTelemetryClient.trackException(e);


            petStoreTelemetryClient.trackTrace("Caught test exception in getProductsByCategory", SeverityLevel.Warning);

        }
        List<Product> products;

        MDC.put(OPERATION, "getProducts");
        MDC.put(CATEGORY, category);





        String requestId = MDC.get(REQUEST_ID);
        String traceId = MDC.get(TRACE_ID);

        log.info("Starting product retrieval operation [RequestID: {}, TraceID: {}, Category: {}]",
                requestId, traceId, category);

        try {
            this.sessionUser.getTelemetryClient().trackEvent(
                    String.format("PetStoreApp user %s is requesting to retrieve products from the ProductService",
                            this.sessionUser.getName()),
                    this.sessionUser.getCustomEventProperties(), null);



            String userName = this.sessionUser.getName();
             String sessionId = this.sessionUser.getSessionId();

            Map<String, String> properties = new HashMap<>();
            properties.put("username", userName);
            properties.put("sessionId", sessionId);
            properties.put("category", category);


            //track event
            petStoreTelemetryClient.trackEvent("ProductsByCategoryRequested", properties, null);

            String message = String.format("User %s (Session: %s) requested products for category %s.",
                    userName, sessionId, category);
            //track trace
            petStoreTelemetryClient.trackTrace(message, SeverityLevel.Information, properties);




            products = productServiceClient.getProductsByStatus(AVAILABLE.getValue());
            //Custom metric additiion
            int itemCount = products.size();
            //adding custom metric
            petStoreTelemetryClient.trackMetric("ProductsReturnedCount", (double) itemCount);


            this.sessionUser.setProducts(products);

            if (tags.stream().anyMatch(t -> t.getName().equals("large"))) {
                products = products.stream()
                        .filter(product -> category.equals(product.getCategory().getName())
                                && product.getTags().toString().contains("large"))
                        .toList();
            } else {
                products = products.stream()
                        .filter(product -> category.equals(product.getCategory().getName())
                                && product.getTags().toString().contains("small"))
                        .toList();
            }



            log.info("Successfully retrieved {} products for category {} with tags {} [RequestID: {}, TraceID: {}]",
                    products.size(), category, tags, requestId, traceId);


            return products;
        } catch (FeignException fe) {
            log.error("Feign error retrieving products [RequestID: {}, TraceID: {}, Category: {}, HTTP: {}, Message: {}]",
                    requestId, traceId, category, fe.status(), fe.getMessage(), fe);

            this.sessionUser.getTelemetryClient().trackException(fe);
            this.sessionUser.getTelemetryClient().trackEvent(
                    String.format("PetStoreApp %s received Feign error %s (HTTP %d), container host: %s",
                            this.sessionUser.getName(),
                            fe.getMessage(),
                            fe.status(),
                            this.containerEnvironment.getContainerHostName())
            );
            log.error("Failed to retrieve products from ProductService via Feign client", fe);
            throw new ProductServiceException("Unable to retrieve products from product service", fe);
        } finally {
            MDC.remove(OPERATION);
            MDC.remove(CATEGORY);
        }
    }
}
