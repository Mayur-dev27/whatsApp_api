# WhatsApp Clone API

This project is a **WhatsApp Clone API** built using **Java**, **Spring Boot**, **Web Socket** and **JWT**. It provides core features for a messaging application, including user authentication, contact management, and messaging functionalities. The goal of this project is to demonstrate a scalable backend architecture for a chat application.

---

## Features 

- **User Authentication**: Register and log in using JWT-based authentication.
- **Real-Time Messaging**: Send and receive messages in real-time.
- **Contact Management**: Add, remove, and view contacts.
- **Group Chat**: Create and manage group chats.
- **Message History**: Retrieve chat history for users and groups.
- **Search Functionality**: Search users, contacts, and messages.
- **API Documentation**: Integrated Swagger UI for interactive API exploration.

---

## Tech Stack

- **Backend**: Java, Spring Boot
- **Authentication**: JSON Web Tokens (JWT)
- **API Documentation**: Swagger
- **Database**: MySQL (or any other relational database)
- **ORM**: Hibernate JPA
- **Build Tool**: Maven

---

## Project Structure

```
whatsapp-clone-api/
|-- src/main/java/com/example/whatsappclone
|   |-- controller
|   |-- dto
|   |-- entity
|   |-- repository
|   |-- service
|   |-- utils
|-- src/main/resources
|   |-- application.properties
|-- pom.xml
```

- **controller**: Handles API endpoints.
- **dto**: Data Transfer Objects for request and response handling.
- **entity**: JPA entities for database mapping.
- **repository**: Interfaces for database operations.
- **service**: Business logic layer.
- **utils**: Utility classes for common functionalities.

---

## Endpoints

### User Endpoints
- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Authenticate user and return JWT token.

### Contact Endpoints
- **GET /api/contacts**: Retrieve the user's contact list.
- **POST /api/contacts**: Add a new contact.
- **DELETE /api/contacts/{id}**: Remove a contact.

### Messaging Endpoints
- **POST /api/messages/send**: Send a message.
- **GET /api/messages/history**: Retrieve message history.
- **GET /api/messages/search**: Search messages by keywords.

### Group Chat Endpoints
- **POST /api/groups/create**: Create a group.
- **GET /api/groups/{id}**: Retrieve group details.
- **POST /api/groups/{id}/add-member**: Add a member to a group.
- **DELETE /api/groups/{id}/remove-member/{userId}**: Remove a member from a group.

---

## API Documentation

Swagger UI has been integrated into this project for easy exploration and testing of API endpoints.

- **Access Swagger UI**: Visit `http://localhost:8080/swagger-ui.html` after running the application.
- **Swagger Configuration**: The Swagger configuration file is included in the project.

---

## How to Run

### Prerequisites
- Java 8 or later
- Maven
- MySQL (or other database)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/whatsapp-clone-api.git
   ```
2. Navigate to the project directory:
   ```bash
   cd whatsapp-clone-api
   ```
3. Configure the database:
   - Update `src/main/resources/application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/whatsapp_clone
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```
4. Build and run the project:
   ```bash
   mvn spring-boot:run
   ```
5. Access the API at `http://localhost:8080`.
6. Open Swagger UI at `http://localhost:8080/swagger-ui.html` to explore the API.

---

## Future Enhancements
- Add support for media files (images, videos, and documents).
- Implement WebSocket for real-time communication.
- Add push notifications for new messages.
- Enhance search functionality with advanced filters.
- Improve user roles and permissions management.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contributing

Contributions are welcome! If you have suggestions for improvements or find bugs, feel free to create an issue or submit a pull request.
