# Random Team Generator – Backend

This is the Spring Boot backend for the Random Team Generator assignment. It provides REST APIs for managing players, teams, and generating skill-balanced teams.

## Features
- CRUD for Players and Teams
- Generate balanced teams based on player skill level
- Save generation sessions with public UUID links
- Proper input validation and error handling
- PostgreSQL as relational database

## Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Lombok
- RESTful APIs

## Running Locally

1. Create a PostgreSQL database (e.g. `random_team_db`)
2. Update `application.properties`:
   1. spring.datasource.url=jdbc:postgresql://localhost:5432/random_team_db
   2. spring.datasource.username=YOUR_USERNAME 
   3. spring.datasource.password=YOUR_PASSWORD
3. Run the application using your IDE or:
    ```bash
    ./mvnw spring-boot:run
    ```
4. The API will be available at: http://localhost:8080/api