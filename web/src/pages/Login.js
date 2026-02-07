import React, { useState } from 'react';

export default function Login({onLogin}){
  const [form, setForm] = useState({email:'', password:''});
  const [msg, setMsg] = useState('');
  const [msgType, setMsgType] = useState('');

  const submit = async e => {
    e.preventDefault();
    
    if (!form.email || !form.password) {
      setMsg('Please fill in all fields');
      setMsgType('error');
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/api/auth/login', {
        method:'POST', 
        headers:{'Content-Type':'application/json'}, 
        body:JSON.stringify(form)
      });
      const j = await res.json();
      
      if (res.ok && j.token) { 
        setMsg('✓ Login successful!');
        setMsgType('success');
        setTimeout(() => onLogin(j.token), 500);
      } else {
        setMsg('Invalid email or password');
        setMsgType('error');
      }
    } catch (err) {
      setMsg('Connection error. Check if backend is running.');
      setMsgType('error');
    }
  }

  return (
    <div className="form-card">
      <h2>🔓 Login</h2>
      <form onSubmit={submit}>
        <div className="form-group">
          <label>Email</label>
          <input 
            type="email"
            placeholder="john@example.com" 
            value={form.email} 
            onChange={e=>setForm({...form,email:e.target.value})} 
          />
        </div>
        <div className="form-group">
          <label>Password</label>
          <input 
            type="password" 
            placeholder="••••••••"
            value={form.password} 
            onChange={e=>setForm({...form,password:e.target.value})} 
          />
        </div>
        <button type="submit">Sign In</button>
      </form>
      {msg && <div className={`message ${msgType}`}>{msg}</div>}
    </div>
  )
}
