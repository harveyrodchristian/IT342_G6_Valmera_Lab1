# IT342 Android Mobile App

## Overview
Android Kotlin mobile application for the IT342 Auth System. Built with Jetpack Compose for modern UI.

## Features
- User Registration
- User Login
- Protected Dashboard (Profile view)
- Logout functionality
- JWT token-based authentication
- Connection to Spring Boot backend via Retrofit

## Tech Stack
- **UI Framework**: Jetpack Compose
- **Navigation**: Navigation Compose
- **HTTP Client**: Retrofit 2 + OkHttp
- **Coroutines**: Kotlin Coroutines
- **Authentication**: JWT (Bearer Token)

## Build Requirements
- Android SDK 24+
- Kotlin 1.9+
- Gradle 8.x

## Configuration
Before building, update the backend URL in `RetrofitClient.kt`:

```kotlin
private const val BASE_URL = "http://<YOUR_BACKEND_IP>:8080/api/"
```

For local testing:
- On Android Studio Emulator: `http://10.0.2.2:8080/api/`
- On Physical Device: Use your machine's local IP address

## Build & Run

### Using Android Studio
1. Open the `mobile` folder as a project in Android Studio
2. Sync Gradle files
3. Update the BASE_URL in `RetrofitClient.kt`
4. Run on emulator or physical device

### Using Command Line
```bash
cd mobile
./gradlew build
./gradlew installDebug  # Install on connected device or emulator
```

## Project Structure
```
mobile/
├── src/main/
│   ├── kotlin/com/example/it342auth/
│   │   ├── MainActivity.kt (Navigation setup)
│   │   ├── api/
│   │   │   ├── ApiService.kt (Retrofit interface)
│   │   │   ├── ApiModels.kt (Data classes)
│   │   │   └── RetrofitClient.kt (HTTP client config)
│   │   └── ui/
│   │       ├── screens/
│   │       │   ├── LoginScreen.kt
│   │       │   ├── RegisterScreen.kt
│   │       │   └── DashboardScreen.kt
│   │       └── theme/
│   │           └── Theme.kt
│   └── AndroidManifest.xml
├── build.gradle
└── README.md
```

## API Endpoints Used
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout
- `GET /api/user/me` - Get current user info

## Security Notes
- JWT tokens are stored in memory (not persisted in SharedPreferences)
- All API calls use HTTPS in production (configure in RetrofitClient)
- Passwords are encrypted on the backend using BCrypt

## Testing
Test the app with these credentials:
1. Register a new user with name, email, and password
2. Login with email and password
3. View profile on Dashboard
4. Logout to return to login screen

## Notes
- The app currently uses `usesCleartextTraffic="true"` in AndroidManifest.xml for local development
- In production, this should be removed and proper HTTPS should be used
