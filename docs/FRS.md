# Functional Requirements Specification (FRS)
# IT342 User Registration and Authentication System

## Version 1.0 | Release Date: February 2026

---

## 1. EXECUTIVE SUMMARY

The IT342 User Registration and Authentication System is a full-stack application designed to provide secure user registration, login, and profile management across web and mobile platforms. The system implements JWT-based authentication with encrypted passwords, ensuring security and scalability.

**Scope**: Backend (Spring Boot), Web Frontend (React), Mobile Frontend (Android Kotlin)

---

## 2. SYSTEM OVERVIEW

### 2.1 Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                     Web App (React)                          │
│     ├─ Login Screen                                          │
│     ├─ Register Screen                                       │
│     ├─ Dashboard Screen                                      │
│     └─ Session Management (JWT)                              │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   │ HTTPS / REST API
                   │
┌──────────────────▼──────────────────────────────────────────┐
│              Backend (Spring Boot)                           │
│     ├─ Authentication Controller                             │
│     ├─ User Controller                                       │
│     ├─ JWT Utility                                           │
│     ├─ Password Encryption (BCrypt)                          │
│     └─ CORS Configuration                                    │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   │ JDBC
                   │
┌──────────────────▼──────────────────────────────────────────┐
│              Database (MySQL 8.0)                            │
│     └─ User Table (id, name, email, password_hash)           │
└─────────────────────────────────────────────────────────────┘
                   │
                   │ HTTPS / REST API
                   │
┌──────────────────▼──────────────────────────────────────────┐
│              Mobile App (Android Kotlin)                     │
│     ├─ Login Screen                                          │
│     ├─ Register Screen                                       │
│     ├─ Dashboard Screen                                      │
│     └─ Session Management (JWT)                              │
└─────────────────────────────────────────────────────────────┘
```

### 2.2 Technology Stack
| Component | Technology |
|-----------|-----------|
| Backend | Spring Boot 2.7+ |
| Web Frontend | React 18 |
| Mobile Frontend | Android Kotlin + Jetpack Compose |
| Database | MySQL 8.0 |
| Authentication | JWT (JSON Web Tokens) |
| Password Encryption | BCrypt |
| HTTP Client | Retrofit 2 (Mobile), Axios (Web) |

---

## 3. DATABASE SCHEMA (ERD DESCRIPTION)

### 3.1 User Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_email ON users(email);
```

**Fields**:
- `id`: Unique user identifier (Primary Key)
- `name`: User's full name
- `email`: Unique email address (for login)
- `password_hash`: BCrypt encrypted password
- `created_at`: User creation timestamp
- `updated_at`: Last modification timestamp

---

## 4. API SPECIFICATIONS

### 4.1 Authentication Endpoints

#### 4.1.1 User Registration
**Endpoint**: `POST /api/auth/register`

**Request**:
```json
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "SecurePassword123"
}
```

**Response (Success - 200)**:
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
}
```

**Response (Error - 400/409)**:
```json
{
    "error": "Email already exists"
}
```

**Implementation Notes**:
- Password must be minimum 6 characters
- Email validation is performed
- BCrypt encryption with salt rounds = 10
- Duplicate email prevention

---

#### 4.1.2 User Login
**Endpoint**: `POST /api/auth/login`

**Request**:
```json
{
    "email": "john@example.com",
    "password": "SecurePassword123"
}
```

**Response (Success - 200)**:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response (Error - 401)**:
```json
{
    "error": "invalid_credentials"
}
```

**Implementation Notes**:
- JWT token generated on successful authentication
- Token expiration: 24 hours (configurable)
- Password comparison uses BCrypt
- Rate limiting recommended for production

---

#### 4.1.3 User Logout
**Endpoint**: `POST /api/auth/logout`

**Headers**:
```
Authorization: Bearer {token}
```

**Response (Success - 200)**:
```json
{
    "message": "logged_out_successfully"
}
```

**Response (Error - 401)**:
```json
{
    "error": "missing_token"
}
```

**Implementation Notes**:
- Client-side token deletion is primary logout mechanism
- Server-side token blacklist optional for production
- JWT tokens are stateless and self-validating

---

### 4.2 User Endpoints

#### 4.2.1 Get Current User Info
**Endpoint**: `GET /api/user/me`

**Headers**:
```
Authorization: Bearer {token}
```

**Response (Success - 200)**:
```json
{
    "id": 1,
    "email": "john@example.com",
    "name": "John Doe"
}
```

**Response (Error - 401)**:
```json
{
    "error": "invalid_token"
}
```

**Response (Error - 404)**:
```json
{
    "error": "user_not_found"
}
```

**Implementation Notes**:
- Token validation via JWT parsing
- Protected endpoint requires Bearer token
- Returns current authenticated user information
- Used for profile verification and dashboard loading

---

## 5. FUNCTIONAL REQUIREMENTS

### 5.1 Registration Component
**FR-1.1**: User can register with name, email, and password
- Valid input validation is performed
- Password strength enforced (min 6 characters)
- Email uniqueness enforced
- Success response with user ID
- Error handling for duplicate emails

**FR-1.2**: Password is encrypted before storage
- BCrypt algorithm with salt (10 rounds)
- Original password never stored in database
- Encryption happens server-side

**FR-1.3**: Registration form includes validation feedback
- Real-time field validation
- Error messages displayed for invalid inputs
- Success confirmation message

---

### 5.2 Login Component
**FR-2.1**: User can login with email and password
- Valid email and password required
- Case-sensitive password comparison
- JWT token generated on success
- Token persisted in client (memory/localStorage)

**FR-2.2**: Failed login attempts show error messages
- Invalid email shows generic error message
- Invalid password shows generic error message
- Rate limiting prevents brute force (recommendation)

**FR-2.3**: Login redirects to dashboard on success
- Token stored and used for subsequent requests
- Protected route access enabled
- Auto-redirect on token expiration

---

### 5.3 Dashboard / Profile Component
**FR-3.1**: Protected dashboard displays user information
- Only accessible with valid JWT token
- Shows user name, email, and status
- Auto-fetches user data from /user/me endpoint
- Loading states during data fetch

**FR-3.2**: Logout functionality available on dashboard
- One-click logout button
- Clears token from client storage
- Redirects to login page
- Server-side logs logout event

**FR-3.3**: Protected pages not accessible when logged out
- Invalid/missing token triggers redirect to login
- Token expiration prevents page access
- Auto-logout on token expiration

---

### 5.4 Web Application Features
**FR-4.1**: React web app implements all above features
- Single-page application (SPA)
- React Router for navigation
- State management via React hooks
- Responsive design (desktop and tablet)
- CSS styling for modern UI

**FR-4.2**: Web app CORS enabled
- Configured to accept requests from localhost:3000, 3001
- Production URLs added on deployment
- Credentials included in cross-origin requests

---

### 5.5 Mobile Application Features
**FR-5.1**: Android Kotlin app implements all above features
- Jetpack Compose for UI
- Navigation Compose for screen navigation
- Same authentication flow as web app
- Retrofit for API calls
- Coroutines for asynchronous operations

**FR-5.2**: Mobile app connects to backend
- BASE_URL configurable in RetrofitClient
- OkHttp logging interceptor for debugging
- Automatic token injection in Authorization header
- Error handling for network failures

**FR-5.3**: Mobile UI implements design patterns
- Material Design 3 components
- Consistent color scheme
- Loading indicators during API calls
- Error states with retry options

---

## 6. NON-FUNCTIONAL REQUIREMENTS

### 6.1 Security
- **NFR-1.1**: All passwords encrypted using BCrypt (cost factor 10)
- **NFR-1.2**: JWT tokens signed with HS256 algorithm
- **NFR-1.3**: HTTPS enforced in production
- **NFR-1.4**: CORS validation on all endpoints
- **NFR-1.5**: SQL injection prevention via parameterized queries
- **NFR-1.6**: XSS prevention via framework sanitization

### 6.2 Performance
- **NFR-2.1**: API response time < 200ms (excluding network latency)
- **NFR-2.2**: Database queries indexed on email field
- **NFR-2.3**: Connection pooling configured (10-20 connections)

### 6.3 Scalability
- **NFR-3.1**: JWT stateless design supports horizontal scaling
- **NFR-3.2**: Database can handle 100,000+ users
- **NFR-3.3**: Load balancer compatible architecture

### 6.4 Availability
- **NFR-4.1**: 99.5% uptime SLA target
- **NFR-4.2**: Graceful error handling in all components
- **NFR-4.3**: Database connection timeout handling

### 6.5 Usability
- **NFR-5.1**: UI responsive on screens from 320px to 2560px width
- **NFR-5.2**: Touch-friendly mobile UI (minimum 44px tap targets)
- **NFR-5.3**: Form validation feedback within 500ms

---

## 7. USER INTERFACE SPECIFICATIONS

### 7.1 Web App Screens

#### 7.1.1 Login Screen (Web)
**Description**: Initial landing page for user authentication
- Header: "🔐 IT342 Auth System"
- Email input field with validation
- Password input field (masked)
- Login button
- "Don't have an account?" link to registration
- Error message display area
- Loading indicator during submission

**Layout**: Centered form on full-screen background
**Design**: Modern, clean with purple accent color
**Validation**: Real-time email and password validation

---

#### 7.1.2 Register Screen (Web)
**Description**: User registration page
- Header: "🔐 Register"
- Name input field
- Email input field with validation
- Password input field (masked)
- Confirm password input field (masked)
- Register button
- "Already have an account?" link to login
- Error message display area
- Loading indicator during submission

**Validation**:
- All fields required
- Passwords must match
- Email format validation
- Password minimum 6 characters

---

#### 7.1.3 Dashboard Screen (Web)
**Description**: User profile and home screen after login
- Header: "👤 Dashboard"
- User profile card showing:
  - Greeting: "Welcome, [User Name]!"
  - Email: [user.email]
  - Status: Active
- Logout button (red/error color)
- Loading state while fetching user data
- Error state with retry option

**Layout**: Centered card layout with logout button below
**Navigation**: Protected page - redirect to login if no token

---

### 7.2 Mobile App Screens

#### 7.2.1 Login Screen (Mobile)
**Description**: Mobile version of login screen
- Header: "🔐 IT342 Auth System"
- Email input field with keyboard optimization
- Password input field (masked)
- Login button (full width, 48px height)
- "Don't have an account?" text button
- Error message display
- Loading indicator (circular)

**Responsive**: Optimized for phones (vertical single column)
**Material Design**: Material 3 color scheme and components

---

#### 7.2.2 Register Screen (Mobile)
**Description**: Mobile registration screen
- Header: "🔐 Register"
- Name input field
- Email input field
- Password input field
- Confirm password input field
- Register button (full width, 48px height)
- "Already have an account?" text button
- Error messages

**Responsive**: Touch-friendly with 16dp padding
**Keyboard**: Proper input types for email and password fields

---

#### 7.2.3 Dashboard Screen (Mobile)
**Description**: Mobile user profile screen
- Header: "👤 Dashboard"
- User profile card (Material Design card):
  - Welcome message
  - Email display
  - Status display
- Logout button (full width, error color, 48px height)
- Loading spinner during data fetch
- Error state with retry

**Responsive**: Full screen with proper spacing
**Accessibility**: Large touch targets, high contrast colors

---

## 8. SECURITY CONSIDERATIONS

### 8.1 Password Security
- Passwords hashed using BCrypt with cost factor 10
- Minimum password length: 6 characters (enforced on client and server)
- Password never logged or transmitted in plaintext
- HTTP Basic Auth not used; JWT Bearer tokens used instead

### 8.2 Token Security
- JWT tokens signed with HS256 algorithm
- Token expiration: 24 hours (configurable)
- Tokens stored:
  - Web: localStorage or memory
  - Mobile: Memory (app lifecycle)
- Token refresh mechanism: Re-login required after expiration

### 8.3 Communication Security
- HTTPS recommended for production (set `usesCleartextTraffic="false"` in mobile app)
- CORS whitelist implemented on backend
- No sensitive data in URL parameters

### 8.4 Input Validation
- Email validation on both client and server
- Password complexity validation on server
- SQL query parameterization prevents injection
- XSS prevention via framework escaping

### 8.5 Authorization
- All protected endpoints require valid Bearer token
- Token claims verified for user identity
- User can only access own profile data

---

## 9. ERROR HANDLING

### 9.1 Common API Error Responses

| HTTP Status | Error | Cause | Resolution |
|------------|-------|-------|-----------|
| 400 | invalid_input | Missing/invalid fields | Validate input format |
| 401 | invalid_credentials | Wrong email/password | Verify credentials |
| 401 | missing_token | No Authorization header | Include Bearer token |
| 401 | invalid_token | Token expired/invalid | Re-login |
| 409 | email_exists | Email already registered | Use unique email |
| 404 | user_not_found | User ID not found | Contact support |
| 500 | server_error | Internal server error | Retry or contact support |

### 9.2 Client-Side Error Handling
- Network errors display retry button
- Timeout errors after 15 seconds
- Loading states prevent duplicate submissions
- Error messages cleared on user input

---

## 10. TESTING SCENARIOS

### 10.1 Registration Testing
```
Test Case 1: Valid Registration
Input: name="John Doe", email="john@example.com", password="Pass123"
Expected: User created, success message, redirect to login

Test Case 2: Duplicate Email
Input: Email already registered
Expected: Error message "Email already exists"

Test Case 3: Weak Password
Input: password="123"
Expected: Error message "Password too short"

Test Case 4: Invalid Email
Input: email="notanemail"
Expected: Error message "Invalid email format"
```

### 10.2 Login Testing
```
Test Case 1: Valid Credentials
Input: Registered email and correct password
Expected: JWT token generated, redirect to dashboard

Test Case 2: Wrong Password
Input: Registered email and wrong password
Expected: Error message "Invalid credentials"

Test Case 3: Unregistered Email
Input: Email not in database
Expected: Error message "Invalid credentials" (generic)

Test Case 4: Missing Fields
Input: Empty email or password
Expected: Error message "Required fields missing"
```

### 10.3 Dashboard Testing
```
Test Case 1: Valid Token
Input: Logged-in user accessing dashboard
Expected: User profile displayed with name and email

Test Case 2: Expired Token
Input: Old/expired JWT token
Expected: Redirect to login page

Test Case 3: Missing Token
Input: No token in header
Expected: Redirect to login page

Test Case 4: Logout
Input: User clicks logout
Expected: Token cleared, redirect to login, dashboard inaccessible
```

---

## 11. DEPLOYMENT INSTRUCTIONS

### 11.1 Backend Deployment
```bash
# Build Spring Boot application
mvn clean package

# Configure application.properties for production database
# Run the JAR
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

**Configuration**:
- Database URL: Update to production MySQL instance
- JWT Secret: Set environment variable JAVA_TOOL_OPTIONS="-DJWT_SECRET=your_secure_key"
- CORS Origins: Update to production domain

### 11.2 Web App Deployment
```bash
# Build React application
npm run build

# Deploy build folder to web server (Nginx, Apache, or S3)
# Update API_BASE_URL to production backend URL
```

### 11.3 Mobile App Deployment
```bash
# Build Android APK
./gradlew build

# For Play Store:
./gradlew bundleRelease

# Update RetrofitClient.kt BASE_URL to production IP/domain
```

---

## 12. FUTURE ENHANCEMENTS

- Email verification for new registrations
- Password reset functionality
- Two-factor authentication (2FA)
- User profile photo upload
- Role-based access control (RBAC)
- OAuth 2.0 integration (Google, GitHub)
- Admin dashboard for user management
- Email notifications
- Activity logging and analytics
- Push notifications for mobile

---

## 13. APPENDIX - SCREENSHOTS GUIDE

To generate a complete FRS PDF, include the following screenshots:

### Web App Screenshots Needed:
1. **Login Screen** (initial state with empty fields)
2. **Login Screen** (with validation error)
3. **Register Screen** (empty form)
4. **Register Screen** (with error message)
5. **Dashboard Screen** (logged-in state with user info)
6. **Dashboard Screen** (after logout - redirect)

### Mobile App Screenshots Needed:
1. **Mobile Login Screen** (portrait orientation)
2. **Mobile Login Screen** (with error message)
3. **Mobile Register Screen** (showing all fields)
4. **Mobile Register Screen** (password mismatch error)
5. **Mobile Dashboard Screen** (showing user profile card)
6. **Mobile Dashboard Screen** (with logout button highlighted)

### Diagrams Needed:
1. **Database ERD** (users table with relationships)
2. **API Flow Diagram** (register → login → dashboard)
3. **Authentication Flow** (JWT token exchange)
4. **System Architecture Diagram** (as shown in section 2.1)

---

## DOCUMENT INFORMATION

| Field | Value |
|-------|-------|
| Document Version | 1.0 |
| Last Updated | February 2026 |
| Author | IT342 Development Team |
| Status | Final |
| Approval | Pending Review |
| Classification | Internal Use |

---

**End of Functional Requirements Specification**
