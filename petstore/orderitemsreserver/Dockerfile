# Stage 1: Build with Maven and JDK 21
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better build caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the function app
RUN mvn clean package
RUN mvn azure-functions:package

# Stage 2: Azure Functions Java image (Java 17, as required by Azure Functions)
FROM mcr.microsoft.com/azure-functions/java:4-java17



# Copy only the built function app from the previous stage
COPY --from=build /app/target/azure-functions/orderitemsreserver-app/ /home/site/wwwroot
COPY --from=build /app/src/main/resources/host.json /home/site/wwwroot
COPY --from=build /app/src/main/resources/local.settings.json /home/site/wwwroot

WORKDIR /home/site/wwwroot

# Azure Functions listens on port 80 inside the container
EXPOSE 8084
