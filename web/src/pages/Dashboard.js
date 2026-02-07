import React, { useEffect, useState } from 'react';

export default function Dashboard({token, onLogout}){
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(()=>{
    const fetchProfile = async () => {
      try {
        const res = await fetch('http://localhost:8080/api/user/me', {
          headers:{'Authorization':'Bearer '+token}
        });
        const data = await res.json();
        setProfile(data);
      } catch(err) {
        setProfile({error:'Failed to fetch profile'});
      } finally {
        setLoading(false);
      }
    };
    fetchProfile();
  },[token]);

  if (loading) {
    return (
      <div className="dashboard-container">
        <div className="dashboard-card">
          <h2>⏳ Loading...</h2>
        </div>
      </div>
    );
  }

  if (profile?.error) {
    return (
      <div className="dashboard-container">
        <div className="dashboard-card">
          <h2>❌ Error</h2>
          <div style={{textAlign: 'center', color: '#dc3545'}}>
            {profile.error}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <h2>👤 Profile Dashboard</h2>
        
        {profile && (
          <div className="profile-info">
            <p>
              <span className="profile-label">Name:</span>
              {profile.name}
            </p>
            <p>
              <span className="profile-label">Email:</span>
              {profile.email}
            </p>
            <p>
              <span className="profile-label">User ID:</span>
              {profile.id}
            </p>
          </div>
        )}

        <button className="logout-btn" onClick={onLogout}>
          🚪 Logout
        </button>
      </div>
    </div>
  )
}
