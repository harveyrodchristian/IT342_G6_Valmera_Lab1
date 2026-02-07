import React, { useState } from 'react';
import Register from './pages/Register';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';

export default function App(){
  const [token, setToken] = useState(null);
  
  return (
    <div className="app-container">
      <div className="app-header">
        <h1>🔐 IT342 Auth System</h1>
      </div>
      <div className="app-content">
        {!token && (
          <div className="auth-container">
            <Register />
            <Login onLogin={setToken} />
          </div>
        )}
        {token && <Dashboard token={token} onLogout={()=>setToken(null)} />}
      </div>
    </div>
  )
}
