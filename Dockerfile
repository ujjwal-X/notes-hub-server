# Use official OpenJDK 17 base image
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy project files
COPY . .

# Give Maven wrapper permission to execute
RUN chmod +x mvnw

# Build Spring Boot application
RUN ./mvnw clean package -DskipTests

# Expose the port (optional, good practice)
EXPOSE 8080

# Run the Spring Boot JAR (Render provides PORT via env variable)
CMD ["sh", "-c", "java -jar target/*.jar"]
