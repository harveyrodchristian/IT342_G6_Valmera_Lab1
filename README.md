# IT342_G6_Valmera_Lab1

## IT342 User Registration and Authentication System

This repository contains a complete full-stack implementation of a user registration and authentication system with backend (Spring Boot), web frontend (React), and mobile frontend (Android Kotlin).

### 📋 Project Overview

The IT342 Auth System demonstrates a modern approach to building scalable production-ready applications with:
- **Secure authentication** using JWT tokens and BCrypt password encryption
- **Cross-platform support** with web and mobile applications
- **RESTful API** architecture with proper error handling
- **Database persistence** using MySQL
- **CORS configuration** for secure cross-origin requests

### 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Web App (React)                          │
│              React 18 | Modern UI | SPA                      │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ├─────── HTTPS / REST API ─────────┐
                   │                                   │
┌──────────────────▼──────────────────────────────────▼─────────┐
│              Backend (Spring Boot)                            │
│    JWT Auth | BCrypt Encryption | CORS Support              │
└──────────────────┬────────────────────────────────────────────┘
                   │
                   ├─────── JDBC ─────────┐
                   │                      │
┌──────────────────▼──────────────────────▼─────────┐
│          Database (MySQL 8.0)                     │
│              User Table + Indexes                  │
└──────────────────┬───────────────────────────────┘
                   │
                   └─────── HTTPS / REST API ─────────┐
                                                      │
┌─────────────────────────────────────────────────────▼────────────┐
│              Mobile App (Android Kotlin)                         │
│    Jetpack Compose | Retrofit | JWT Auth | Coroutines          │
└────────────────────────────────────────────────────────────────┘
```

### 📁 Repository Structure

```
IT342_G6_Valmera_Lab1/
├── README.md (this file)
├── TASK_CHECKLIST.md (Lab 2 & Lab 3 completions with commit hashes)
├── backend/
│   ├── pom.xml (Maven configuration)
│   ├── README.md (Backend documentation)
│   └── src/
│       ├── main/java/com/example/backend/
│       │   ├── DemoApplication.java (Spring Boot entry point)
│       │   ├── config/
│       │   │   └── CorsConfig.java (CORS configuration)
│       │   ├── controller/
│       │   │   ├── AuthController.java (Register, Login, Logout)
│       │   │   └── UserController.java (Get user info)
│       │   ├── model/
│       │   │   └── User.java (User entity)
│       │   ├── repository/
│       │   │   └── UserRepository.java (Database access)
│       │   ├── service/
│       │   │   └── AuthService.java (Business logic)
│       │   └── util/
│       │       └── JwtUtil.java (JWT token handling)
│       └── resources/
│           └── application.properties (Database & server config)
├── web/
│   ├── README.md (Web documentation)
│   ├── package.json (NPM dependencies)
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── App.js (Main component with routing)
│       ├── index.js (React entry point)
│       ├── index.css (Global styles)
│       └── pages/
│           ├── Login.js (Login screen)
│           ├── Register.js (Registration screen)
│           └── Dashboard.js (Protected user profile)
├── mobile/
│   ├── README.md (Mobile documentation & setup guide)
│   ├── AndroidManifest.xml (Android configuration)
│   ├── build.gradle (Gradle build configuration)
│   └── src/main/kotlin/com/example/it342auth/
│       ├── MainActivity.kt (Navigation setup)
│       ├── api/
│       │   ├── ApiService.kt (Retrofit REST client)
│       │   ├── ApiModels.kt (Request/Response models)
│       │   └── RetrofitClient.kt (HTTP client setup)
│       └── ui/
│           ├── screens/
│           │   ├── LoginScreen.kt (Login UI - Jetpack Compose)
│           │   ├── RegisterScreen.kt (Registration UI)
│           │   └── DashboardScreen.kt (Profile UI)
│           └── theme/
│               └── Theme.kt (Material Design 3 theme)
└── docs/
    ├── FRS.md (Complete Functional Requirements Specification)
    │   ├── System Architecture
    │   ├── Database Schema
    │   ├── API Specifications
    │   ├── UI Specifications
    │   ├── Security Considerations
    │   ├── Testing Scenarios
    │   └── Deployment Instructions
    └── FRS_PLACEHOLDER.md (Old - can be removed)
```

### 🚀 Quick Start

#### Backend (Spring Boot)
```bash
cd backend

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
# Or: java -jar target/backend-0.0.1-SNAPSHOT.jar

# Backend will start on http://localhost:8080
```

**Configuration**:
- Database: MySQL (update `application.properties` with your database URL)
- Default DB connection: `jdbc:mysql://localhost:3306/it342_lab`
- Ensure MySQL is running (XAMPP or standalone)

#### Web Application (React)
```bash
cd web

# Install dependencies
npm install

# Start development server
npm start
# Application runs on http://localhost:3000

# Build for production
npm run build
```

#### Mobile Application (Android)
```bash
cd mobile

# Update BASE_URL in src/main/kotlin/com/example/it342auth/api/RetrofitClient.kt
# For Android Emulator: http://10.0.2.2:8080/api/
# For Physical Device: http://<YOUR_MACHINE_IP>:8080/api/

# Build with Android Studio or gradle
./gradlew build

# Install debug APK
./gradlew installDebug
```

### 🔐 Security Features

✅ **Authentication & Authorization**
- JWT (JSON Web Tokens) for stateless session management
- Bearer token validation on protected endpoints
- Automatic token expiration after 24 hours

✅ **Password Security**
- BCrypt hashing with cost factor 10
- Server-side password encryption
- No plaintext passwords stored or transmitted

✅ **API Security**
- CORS whitelist configuration
- SQL injection prevention via parameterized queries
- XSS prevention through framework escaping
- Input validation on all endpoints

### 📱 Features

#### Authentication Flow
1. **Register**: Create new account with email and password
2. **Login**: Authenticate and receive JWT token
3. **Dashboard**: View profile (protected page)
4. **Logout**: Clear session and return to login

#### Backend Endpoints
```
POST   /api/auth/register     - Create new user
POST   /api/auth/login        - Authenticate user
POST   /api/auth/logout       - Logout user
GET    /api/user/me           - Get current user info (protected)
```

#### Web App Features
- Single-page application (SPA)
- Responsive design
- Token-based state management
- Modern UI with loading states

#### Mobile App Features
- Native Android Kotlin application
- Jetpack Compose UI framework
- Material Design 3 components
- Retrofit REST client
- Kotlin Coroutines for async operations

### 🧪 Testing

All three platforms (Backend, Web, Mobile) implement the same authentication flow:

```
Test Credentials:
Email: test@example.com
Password: Test123

Test Flow:
1. Register → new user created
2. Login → JWT token generated
3. Dashboard → user info displayed
4. Logout → session cleared
```

### 📖 Documentation

- **FRS (Functional Requirements Specification)**: See [docs/FRS.md](docs/FRS.md)
  - Complete system specification
  - Database schema documentation
  - API endpoint specifications
  - Security considerations
  - Testing scenarios
  - Deployment instructions

- **Backend Setup**: See [backend/README.md](backend/README.md)
- **Web Setup**: See [web/README.md](web/README.md)
- **Mobile Setup**: See [mobile/README.md](mobile/README.md)

### ✅ Completion Status

#### Lab 2 (Backend + Web)
- [x] Spring Boot backend with authentication
- [x] MySQL database
- [x] React web application
- [x] JWT token implementation
- [x] BCrypt password encryption
- [x] CORS configuration
- [x] Error handling

#### Lab 3 (Mobile + Finalization)
- [x] Android Kotlin mobile application
- [x] Jetpack Compose UI
- [x] Retrofit API client
- [x] Logout endpoint
- [x] Complete FRS documentation
- [x] Task checklist with commit hashes
- [x] Security review
- [x] Cross-platform testing

### 🛠️ Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Backend | Spring Boot | 2.7+ |
| | Java | 11+ |
| | Maven | 3.8+ |
| Frontend (Web) | React | 18.2 |
| | Node.js | 18+ |
| Frontend (Mobile) | Android | API 24+ |
| | Kotlin | 1.9+ |
| | Jetpack Compose | 1.5+ |
| Database | MySQL | 8.0 |
| Authentication | JWT | HS256 |
| HTTP Client | Retrofit | 2.9 |
| | OkHttp | 4.10 |

### 📝 Submission Checklist

Before final submission to MS Teams:

- [ ] All code committed and pushed to GitHub
- [ ] Backend tested and running
- [ ] Web app tested and running
- [ ] Mobile app tested and running
- [ ] All endpoints tested with Postman or similar
- [ ] Screenshots taken of Web and Mobile UIs
- [ ] FRS.md updated with screenshots
- [ ] TASK_CHECKLIST.md updated with all commit hashes
- [ ] README.md reviewed and complete
- [ ] Database migrations tested
- [ ] CORS verified for all platforms
- [ ] Security review completed
- [ ] Documentation complete and accurate

### 🔗 GitHub Repository

Submit the repository link to MS Teams

### 📧 Support & Notes

- **Database Setup**: Ensure MySQL is running before starting backend
  ```
  XAMPP: Start MySQL from XAMPP Control Panel
  Or: mysql -u root -p
  ```

- **Backend URL for Mobile**: Update `RetrofitClient.kt` with correct IP/domain
  - Local development: `http://10.0.2.2:8080/api/` (emulator)
  - Physical device: `http://<MACHINE_IP>:8080/api/`

- **CORS Issues**: Ensure backend CORS is configured for frontend URLs

- **JWT Expiration**: Tokens expire after 24 hours (re-login required)

---

**Project Status**: ✅ COMPLETE | Lab 2 & Lab 3 Finished
**Last Updated**: February 2026
**Built By**: IT342 Development Team