# API Files

## About
An API for upload and download of the files

## Technologies used
- Java
- Maven
- Docker
- PostgreSQL
- Springdoc (Open API / Swagger)
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation with Hibernate validator
- Flyway
- Spring Security
- Token JWT

## Swagger Documentation
The API documentation is available on Swagger. To access it, follow these steps:

1. Run project
3. Open a web browser and navigate to `http://localhost:8080/swagger-ui/index.html`
4. The API documentation should be displayed in Swagger

## How to run
Prerequisites:

1. Java 17 or higher (I recommend using OpenJDK 21)
2. PostgreSQL (I recommend using PostgreSQL 15)
3. Configure your database in the application.yaml file

```bash
# Clone this repository
git clone https://github.com/JulioEvencio/api-files.git

# Configure your database in the application.yaml file

# Enter the project folder
cd api-files/api-files

# Execute the project
./mvnw spring-boot:run
```

## Contribution
Please feel free to send pull requests and report issues.
