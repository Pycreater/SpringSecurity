# SpringSecurity - User Registration and Authentication

Welcome to the SpringSecurity project! This application demonstrates a complete user registration, authentication, and authorization flow using Spring Boot, Spring Security, and OAuth2.

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Usage](#usage)
7. [Endpoints](#endpoints)
8. [Testing](#testing)
9. [Contributing](#contributing)
10. [License](#license)
11. [Contact](#contact)

## Introduction

This project showcases a secure user registration and login system with role-based access control. It is built with Spring Boot and integrates Spring Security for authentication and authorization, along with OAuth2 for secure API access.

## Features

- User Registration
- User Login
- Password Encryption
- Role-Based Authorization
- OAuth2 Integration
- JWT Token Generation and Validation
- RESTful API

## Tech Stack

- **Backend:** Spring Boot, Spring Security, OAuth2
- **Database:** MySQL
- **Build Tool:** Maven
- **Testing:** JUnit, Postman

## Installation

### Prerequisites

- Java 11 or higher
- Maven
- MySQL Server

### Steps

1. **Clone the repository:**
    ```sh
    git clone https://github.com/Pycreater/SpringSecurity.git
    cd SpringSecurity
    ```

2. **Configure MySQL:**
    Create a database named `spring_security` and update the `application.properties` file with your MySQL credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/spring_security
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3. **Build the project:**
    ```sh
    mvn clean install
    ```

4. **Run the application:**
    ```sh
    mvn spring-boot:run
    ```

## Configuration

Ensure your `application.properties` is properly configured for database and security settings. For OAuth2, you might need to set up additional properties like client ID, client secret, and other OAuth2 configurations.

## Usage

Once the application is running, you can access the endpoints using a tool like Postman or directly via a web browser. The base URL for the APIs is `http://localhost:8080`.

## Endpoints

- **User Registration:** `POST /api/auth/register`
- **User Login:** `POST /api/auth/login`
- **Get User Details:** `GET /api/user/{id}`
- **Protected Resource:** `GET /api/protected`

Refer to the API documentation for detailed request and response formats.

## Testing

To run the tests, use the following command:
```sh
mvn test
```

### Testing with Postman
You can also use Postman to manually test the endpoints. A Postman collection is included in the repository.

## Contributing
Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a pull request

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
Author: Your Name  
Email: [pratikyadav3949@gmail.com](mailto:pratikyadav3949@gmail.com)  
LinkedIn: [(https://www.linkedin.com/in/your-profile)](https://www.linkedin.com/in/pratikyadav3949/)
