# IT342 Web Application - React

## Overview
Modern single-page application (SPA) for user registration, authentication, and profile management. Built with React 18 and connected to Spring Boot backend via REST API.

## Features
- ✅ User registration with validation
- ✅ Secure login with JWT tokens
- ✅ Protected user dashboard
- ✅ User profile display
- ✅ Logout functionality
- ✅ Responsive design
- ✅ Real-time form validation
- ✅ Loading states and error handling

## Technology Stack
- **Framework**: React 18.2
- **Build Tool**: Create React App
- **HTTP Client**: Fetch API
- **Package Manager**: npm 8+
- **Node.js**: 18+

## Prerequisites
- Node.js 18+ with npm
- Backend API running on http://localhost:8080
- Modern web browser

## Installation & Setup

### 1. Install Dependencies

Navigate to the `web` directory and install npm packages:

```bash
cd web
npm install
```

### 2. Update Backend URL (if needed)

The app is configured to connect to `http://localhost:8080`. If your backend runs on a different address, update the fetch URLs in:
- `src/pages/Login.js` (line 17)
- `src/pages/Register.js`
- `src/pages/Dashboard.js` (line 10)

### 3. Start Development Server

```bash
npm start
```

The application will open automatically at `http://localhost:3000`

### 4. Build for Production

```bash
npm run build
```

Creates an optimized production build in the `build/` directory.

## Project Structure

```
web/
├── package.json (Dependencies and scripts)
├── public/
│   └── index.html (HTML entry point)
├── src/
│   ├── App.js (Main component with routing logic)
│   ├── index.js (React DOM render)
│   ├── index.css (Global styles)
│   └── pages/
│       ├── Login.js (Login screen component)
│       ├── Register.js (Registration screen component)
│       └── Dashboard.js (Protected profile screen)
└── README.md (this file)
```

## Component Architecture

### App.js (Main Component)
- Manages authentication state (`token`)
- Handles routing between screens
- Passes props to child components

**State:**
- `token`: Stores JWT token from login (null = logged out)

**Logic:**
- Shows Register/Login screens if no token
- Shows Dashboard if token exists
- Passes `setToken` to Login for authentication
- Passes `onLogout` to Dashboard for logout

### Login.js (Login Screen)
Allows users to authenticate with email and password

**Features:**
- Email and password input fields
- Form validation
- API call to `/api/auth/login`
- JWT token storage
- Error message display
- Link to registration page
- Loading indicator during submission

**Form State:**
```javascript
{ email: '', password: '' }
```

**API Call:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{ "email": "user@example.com", "password": "password123" }
```

**Expected Response:**
```json
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

### Register.js (Registration Screen)
Allows new users to create an account

**Features:**
- Name, email, and password input fields
- Password confirmation field
- Form validation
- API call to `/api/auth/register`
- Password match validation
- Error message display
- Link to login page
- Loading indicator during submission

**Form State:**
```javascript
{ name: '', email: '', password: '', confirmPassword: '' }
```

**API Call:**
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{ 
  "name": "John Doe",
  "email": "john@example.com", 
  "password": "password123" 
}
```

**Expected Response:**
```json
{ "id": 1, "name": "John Doe", "email": "john@example.com" }
```

### Dashboard.js (Protected Profile Screen)
Displays user profile information (accessible only when logged in)

**Features:**
- Fetches user data from `/api/user/me`
- Displays user name and email
- Shows loading state while fetching
- Error handling with error display
- Logout button
- Protected with token validation

**API Call:**
```
GET http://localhost:8080/api/user/me
Authorization: Bearer {token}
```

**Expected Response:**
```json
{ "id": 1, "name": "John Doe", "email": "john@example.com" }
```

## Styling

Global styles are defined in `src/index.css` with:
- Modern color scheme (purple primary color)
- Responsive layout
- Form styling
- Button styling
- Card-based UI components
- Mobile-friendly design

**Key Classes:**
- `.app-container`: Main container
- `.app-header`: Header section
- `.app-content`: Content area
- `.auth-container`: Authentication screens container
- `.form-card`: Form wrapper
- `.form-group`: Individual form field group
- `.dashboard-container`: Dashboard wrapper
- `.dashboard-card`: Dashboard content card

## Authentication Flow

```
1. User registers with name, email, password
   ↓
2. Backend validates and creates user (password hashed)
   ↓
3. User redirected to login page
   ↓
4. User enters email and password
   ↓
5. Backend validates and generates JWT token
   ↓
6. Token stored in React state
   ↓
7. App renders Dashboard component
   ↓
8. Dashboard fetches user data using token
   ↓
9. User profile displayed
   ↓
10. User clicks logout
    ↓
11. Token cleared from state
    ↓
12. App redirects to login screen
```

## Testing Scenarios

### Test 1: New User Registration
```
1. Click "Don't have an account? Register" link
2. Enter name: "Test User"
3. Enter email: "testuser@example.com"
4. Enter password: "TestPass123"
5. Confirm password: "TestPass123"
6. Click "Register"
7. Expect: Success message and redirect to login
8. Verify: User created in database
```

### Test 2: User Login
```
1. Enter email: "testuser@example.com"
2. Enter password: "TestPass123"
3. Click "Login"
4. Expect: Dashboard displayed with user info
5. Verify: Token stored in browser state
```

### Test 3: Dashboard Access
```
1. After login, verify dashboard shows:
   - User name
   - User email
   - Logout button
2. Verify data matches registered user
```

### Test 4: Logout
```
1. Click "Logout" button on dashboard
2. Expect: Return to login screen
3. Verify: Token cleared
4. Try accessing dashboard directly (should not work)
```

### Test 5: Error Handling
```
Test 5a: Invalid credentials
- Login with wrong password
- Expect: "Invalid email or password" error

Test 5b: Empty fields
- Leave email or password blank
- Expect: "Please fill in all fields" error

Test 5c: Backend offline
- Stop backend server
- Try to login
- Expect: "Connection error" message

Test 5d: Password mismatch
- Register with mismatched passwords
- Expect: "Passwords do not match" error
```

## Troubleshooting

### "Cannot GET /" error
**Problem**: App won't load
**Solution**: Ensure `npm start` is running and backend URL is correct

### "Network Error" or "Cannot reach backend"
**Problem**: App can't connect to API
**Solution**: 
1. Ensure backend is running on http://localhost:8080
2. Check CORS configuration in backend
3. Verify firewall settings

### Login fails with valid credentials
**Problem**: Backend returns 401 even with correct credentials
**Solution**:
1. Ensure user exists in database (register first)
2. Check backend logs for errors
3. Verify password is correct

### Tokens persisting after refresh
**Problem**: User stays logged in after page refresh
**Solution**: This is current behavior (tokens stored in memory). For persistence, implement localStorage.

### Styles not loading
**Problem**: Page looks unstyled
**Solution**: 
1. Clear browser cache
2. Ensure `index.css` is properly imported
3. Check browser console for CSS errors

## Performance Optimization

- React.lazy() for code splitting (optional future enhancement)
- Memoization of components to prevent unnecessary re-renders
- Efficient state updates with functional setState
- Network requests optimized with proper error handling

## Production Deployment

### Build the App
```bash
npm run build
```

### Deploy Built Files
1. Copy contents of `build/` folder
2. Deploy to web server (Nginx, Apache, S3, Vercel, etc.)
3. Configure server to serve `index.html` for all routes (SPA routing)

### Production Configuration
```javascript
// Update API base URL for production
const API_BASE_URL = 'https://api.yourdomain.com';
// Update all fetch calls to use production URL
```

### Example Nginx Configuration
```nginx
server {
    listen 80;
    server_name yourdomain.com;
    root /var/www/html;
    
    location / {
        try_files $uri /index.html;
    }
    
    location /api/ {
        proxy_pass http://localhost:8080;
    }
}
```

## Future Enhancements

- [ ] Implement localStorage for token persistence
- [ ] Add password reset functionality
- [ ] Add user profile editing
- [ ] Implement 2FA (Two-Factor Authentication)
- [ ] Add loading skeleton screens
- [ ] Implement toast notifications
- [ ] Add dark mode support
- [ ] Implement progressive web app (PWA)
- [ ] Add unit tests
- [ ] Add end-to-end tests with Cypress

## File Dependencies

Required npm packages (in package.json):
```json
{
  "react": "18.2.0",
  "react-dom": "18.2.0",
  "react-scripts": "5.0.1"
}
```

## Browser Support

- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

## License

This project is for IT342 educational purposes.

---

**Frontend Status**: ✅ Complete
**Last Updated**: February 2026
