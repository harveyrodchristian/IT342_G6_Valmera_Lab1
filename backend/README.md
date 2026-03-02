# IT342 Backend API - Spring Boot

## Overview
RESTful backend API for the IT342 User Registration and Authentication System, built with Spring Boot and secured with JWT authentication and BCrypt password encryption.

## Features
- ✅ User registration with email validation
- ✅ Secure login with JWT token generation
- ✅ User profile retrieval (protected endpoint)
- ✅ Logout functionality
- ✅ BCrypt password encryption
- ✅ MySQL database persistence
- ✅ CORS support for multiple frontends
- ✅ Comprehensive error handling

## Technology Stack
- **Framework**: Spring Boot 2.7+
- **Language**: Java 11+
- **Build Tool**: Maven 3.8+
- **Database**: MySQL 8.0
- **Authentication**: JWT (HS256)
- **Password Encryption**: BCrypt
- **ORM**: Spring Data JPA + Hibernate

## Prerequisites
- Java 11 or higher
- Maven 3.8+
- MySQL 8.0 (running locally or remote)
- Postman (for testing, optional)

## Project Setup

### 1. Database Configuration

Ensure MySQL is running and create the database:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS it342_lab;

-- The application will auto-create the User table using Hibernate
```

### 2. Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/it342_lab?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD  # If you set a password for MySQL

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop  # Change to 'update' for production
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8080

# JWT Configuration (Optional - can be set via environment variables)
jwt.secret.key=your_secret_key_here_make_it_long_and_random
jwt.expiration.time=86400000  # 24 hours in milliseconds
```

### 3. Build the Project

```bash
mvn clean package
```

### 4. Run the Application

#### Using Maven
```bash
mvn spring-boot:run
```

#### Using Java
```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication Endpoints

#### POST /api/auth/register
Create a new user account

**Request:**
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "SecurePass123"
}
```

**Response (200 OK):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
}
```

**Error Responses:**
- `400`: Missing fields or invalid input
- `409`: Email already registered

---

#### POST /api/auth/login
Authenticate user and generate JWT token

**Request:**
```json
{
    "email": "john@example.com",
    "password": "SecurePass123"
}
```

**Response (200 OK):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjczOTYxNDI5LCJleHAiOjE2NzQwNDc4Mjl9.S0Wfx5..."
}
```

**Error Responses:**
- `401`: Invalid email or password

---

#### POST /api/auth/logout
Logout user (server-side confirmation)

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (200 OK):**
```json
{
    "message": "logged_out_successfully"
}
```

**Error Responses:**
- `401`: Missing or invalid token

---

### User Endpoints

#### GET /api/user/me
Get current authenticated user information (Protected)

**Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (200 OK):**
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
}
```

**Error Responses:**
- `401`: Missing or invalid token
- `404`: User not found

---

## Project Structure

```
backend/
├── pom.xml (Maven configuration)
├── src/
│   ├── main/
│   │   ├── java/com/example/backend/
│   │   │   ├── DemoApplication.java (Spring Boot main class)
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java (CORS configuration)
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java (Register, Login, Logout endpoints)
│   │   │   │   └── UserController.java (Get user endpoints)
│   │   │   ├── model/
│   │   │   │   └── User.java (User entity with JPA annotations)
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java (Spring Data JPA repository)
│   │   │   ├── service/
│   │   │   │   └── AuthService.java (Authentication business logic)
│   │   │   └── util/
│   │   │       └── JwtUtil.java (JWT token generation and validation)
│   │   └── resources/
│   │       └── application.properties (Configuration)
│   └── test/ (Test files)
└── README.md (this file)
```

## Key Components

### 1. User Entity (model/User.java)
Represents a user in the system with JPA annotations for database mapping

**Fields:**
- `id`: Unique identifier (Primary Key)
- `name`: User's full name
- `email`: Unique email for login
- `password`: BCrypt hashed password
- `createdAt`: User creation timestamp
- `updatedAt`: Last update timestamp

### 2. AuthService (service/AuthService.java)
Handles authentication business logic

**Methods:**
- `register()`: Create new user with password hashing
- `authenticate()`: Verify credentials and return User

### 3. JwtUtil (util/JwtUtil.java)
Manages JWT token generation and validation

**Methods:**
- `generateToken()`: Create JWT token for authenticated user
- `parseToken()`: Validate and extract claims from token
- `isTokenValid()`: Check token validity and expiration

### 4. Controllers
Define REST endpoints and handle HTTP requests

- **AuthController**: `/api/auth/*` endpoints
- **UserController**: `/api/user/*` endpoints (protected)

### 5. CORS Configuration (config/CorsConfig.java)
Allows requests from frontend applications

**Configured Origins:**
- http://localhost:3000 (React dev server)
- http://localhost:3001 (Alternative port)

## Testing with Postman

### 1. Register a new user
- Method: POST
- URL: `http://localhost:8080/api/auth/register`
- Body (JSON):
```json
{
    "name": "Test User",
    "email": "test@example.com",
    "password": "TestPass123"
}
```

### 2. Login
- Method: POST
- URL: `http://localhost:8080/api/auth/login`
- Body (JSON):
```json
{
    "email": "test@example.com",
    "password": "TestPass123"
}
```
- Save the returned `token`

### 3. Get current user
- Method: GET
- URL: `http://localhost:8080/api/user/me`
- Headers:
  - Key: `Authorization`
  - Value: `Bearer [token]` (replace [token] with actual token)

### 4. Logout
- Method: POST
- URL: `http://localhost:8080/api/auth/logout`
- Headers:
  - Key: `Authorization`
  - Value: `Bearer [token]`

## Security Features

### Password Encryption
- Algorithm: BCrypt
- Cost Factor: 10 (adjustable in AuthService)
- Passwords are salted and hashed on the server
- Original passwords never stored in database

### JWT Authentication
- Algorithm: HS256 (HMAC-SHA256)
- Expiration: 24 hours (configurable)
- Token Format: `Bearer {token}`
- Tokens validated on protected endpoints

### CORS Protection
- Whitelist-based origin validation
- Credentials support enabled
- Preflight requests handled

### Input Validation
- Email format validation
- Required field checks
- SQL injection prevention via JPA
- XSS prevention through framework

## Database Schema

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## Error Responses

All endpoints return consistent error responses:

```json
{
    "error": "error_code",
    "message": "Descriptive error message"
}
```

**Common Error Codes:**
- `invalid_input`: Missing or invalid fields
- `invalid_credentials`: Wrong email/password
- `email_exists`: Email already registered
- `missing_token`: No Authorization header
- `invalid_token`: Token expired or malformed
- `user_not_found`: User ID not found
- `server_error`: Internal server error

## Troubleshooting

### Database Connection Issues
```
Error: Communications link failure
Solution: Ensure MySQL is running and credentials in application.properties are correct
```

### CORS Errors
```
Error: Access to XMLHttpRequest has been blocked by CORS policy
Solution: Check that frontend URL is in CORS whitelist in CorsConfig.java
```

### Token Validation Errors
```
Error: 401 invalid_token
Solution: Ensure Bearer prefix is used: "Bearer [token]"
```

### Password Hashing Issues
```
Error: BCryptException
Solution: Check that password encoding is properly initialized in AuthService
```

## Production Deployment Checklist

- [ ] Update `application.properties` with production database URL
- [ ] Set JWT secret via environment variable
- [ ] Change `ddl-auto` from `create-drop` to `update`
- [ ] Enable HTTPS/SSL certificates
- [ ] Add production frontend URLs to CORS whitelist
- [ ] Set `spring.jpa.show-sql=false`
- [ ] Configure application logs
- [ ] Set up database backups
- [ ] Review security headers
- [ ] Test all endpoints thoroughly

## Future Enhancements

- Email verification
- Password reset functionality
- Two-factor authentication (2FA)
- User roles and permissions
- Activity logging
- Rate limiting
- API versioning
- GraphQL endpoint
- OpenAPI/Swagger documentation

## Support & Contact

For issues or questions:
1. Check the Functional Requirements Specification (FRS.md)
2. Review the Spring Boot logs for detailed error messages
3. Ensure all prerequisites are installed and running
4. Test endpoints with Postman to isolate issues

## License

This project is for IT342 educational purposes.

---

**Backend Status**: ✅ Complete
**Last Updated**: February 2026
