# Spring Framework Learning Application
This application was created as a learning project to get hands-on experience with the Spring Framework. It showcases the use of various Spring mechanisms and integrates several technologies to build a robust web application.

# Features
**Dependency Injection:** Utilizes Spring's powerful dependency injection to manage component lifecycles and dependencies.

**Aspect-Oriented Programming (AOP):** Implements AOP to separate cross-cutting concerns, such as logging and transaction management.

**Database Integration:** Connects to an H2 in-memory database for testing and development purposes.

**Hibernate ORM:** Uses Hibernate for object-relational mapping to manage database interactions.

**Maven:** Builds and manages the project using Maven.

**Application Structure:** Follows a well-defined structure with controllers, services, and configuration classes.

**Spring Security:** Secures the application using Spring Security, integrated with Keycloak for identity and access management.
# Technologies Used
Spring Framework
Core
AOP
Data JPA
Security
H2 Database
Hibernate
Maven
Keycloak
# Getting Started
To get a local copy up and running follow these simple steps.

### Prerequisites
Java 11 or higher
Maven
Keycloak instance (for Spring Security integration)
### Installation
Clone the repository
```
git clone https://github.com/kackan1/TodoAppSpringboot.git
```
Navigate to the project directory
```
cd TodoAppSpringboot
```
Build the project using Maven
```
mvn clean install
```
### Running the Application
Running the application doesn't require setting up Keycloak just some Security features won't be present.
1. Start the Keycloak server and configure a realm for the application.
    - Realm: TodoApp
    - Add users
2. Update the application.properties file with your Keycloak settings.
    - Default is that Application will look for Keycloak server at port 8180
3. Run the Spring Boot application
```
mvn spring-boot:run
```
The application should now be running on http://localhost:8080.

# Security
Spring Security is used to secure the application, integrated with Keycloak for authentication and authorization. Ensure your Keycloak server is configured correctly and running before starting the application.
# Contact
Kacper Kaniuka - Kacper.Kaniuka@wp.pl

Project Link: https://github.com/your-username/your-repository
