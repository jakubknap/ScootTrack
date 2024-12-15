# ScootTrack
>ScootTrack is designed for scooter enthusiasts to efficiently manage their scooters and track maintenance history and expenses. The app helps users monitor the technical condition of their scooters and plan maintenance, enhancing safety and extending the equipment's lifespan

Key features of the application:
- User Support: Register, log in, and activate accounts via email.
- Scooter Management: Add, edit, and delete scooters in the user’s “garage.”
- Repair Management: Add, edit, and delete repairs linked to scooters, view repair history.
- Repair Statistics: Generate repair cost and frequency statistics for the user’s scooters.

## Table of Contents
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Contact](#contact)

## Technologies Used
- Spring Boot 3
- Spring Security 6
- JWT Token Authentication
- Spring Data JPA
- JSR-303 and Spring Validation
- OpenAPI and Swagger UI Documentation
- Docker
- WebSocket

## Setup
0. **Before starting:** You must have docker installed and running

1. Clone the repository:
```bash
git clone https://github.com/jakubknap/ScootTrack.git
```

2. Go to the project directory and start the containers with the command:
```bash
docker compose up -d
```

2. Run the application
```bash
mvn spring-boot:run
```

3. **[Optional]** Run the Spring Boot application using the java -jar command after building through maven<br>
Go to the project directory and run app with the command:
```bash
java -jar .\target\ScootTrack-1.0.0.jar
```
Or use application profiling:
```bash
java -jar "-Dspring.profiles.active=dev" .\target\ScootTrack-1.0.0.jar
```

## API
- You can check the functionality of the backend itself using swagger: http://localhost:8088/swagger-ui.html  Important! Remember about authentication

## Contact
Created by [Jakub Knap](https://www.linkedin.com/in/jakub-knap/) - feel free to contact me!
