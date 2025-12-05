package com.sea.function;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.sea.function.model.OrderRequest;

import java.util.Optional;

public class OrderItemsReserverFunction {
    @FunctionName("OrderItemsReserver")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        try {
            // Parse incoming JSON
            String requestBody = request.getBody().orElse("");
            ObjectMapper mapper = new ObjectMapper();
            OrderRequest orderRequest = mapper.readValue(requestBody, OrderRequest.class);

            // Prepare JSON for upload
            String jsonToUpload = mapper.writeValueAsString(orderRequest);

            // Get connection string from environment variable
            String connectionString = System.getenv("AzureWebJobsStorage");
            String containerName = "orderitems"; // Ensure this container exists

            // Use sessionId for file naming
            String blobName = orderRequest.getSessionId() + ".json";

            // Upload to Blob Storage
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlockBlobClient blobClient = containerClient.getBlobClient(blobName).getBlockBlobClient();

            blobClient.upload(
                    new java.io.ByteArrayInputStream(jsonToUpload.getBytes()),
                    jsonToUpload.getBytes().length,
                    true // Overwrite existing file
            );

            return request.createResponseBuilder(HttpStatus.OK)
                    .body("Order request uploaded successfully for session: " + orderRequest.getSessionId())
                    .build();

        } catch (Exception e) {
            context.getLogger().severe("Error: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing order: " + e.getMessage())
                    .build();
        }
    }
}