import React, { useState } from 'react';

export default function Register(){
  const [form, setForm] = useState({name:'', email:'', password:''});
  const [msg, setMsg] = useState('');
  const [msgType, setMsgType] = useState('');

  const submit = async e => {
    e.preventDefault();
    
    if (!form.name || !form.email || !form.password) {
      setMsg('Please fill in all fields');
      setMsgType('error');
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/api/auth/register', {
        method:'POST', 
        headers:{'Content-Type':'application/json'}, 
        body:JSON.stringify(form)
      });
      
      if (res.ok) {
        setMsg('✓ Registered successfully! You can now login.');
        setMsgType('success');
        setForm({name:'', email:'', password:''});
      } else {
        setMsg('Error registering. Email might already exist.');
        setMsgType('error');
      }
    } catch (err) {
      setMsg('Connection error. Check if backend is running.');
      setMsgType('error');
    }
  }

  return (
    <div className="form-card">
      <h2>📝 Register</h2>
      <form onSubmit={submit}>
        <div className="form-group">
          <label>Full Name</label>
          <input 
            type="text"
            placeholder="John Doe" 
            value={form.name} 
            onChange={e=>setForm({...form,name:e.target.value})} 
          />
        </div>
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
        <button type="submit">Create Account</button>
      </form>
      {msg && <div className={`message ${msgType}`}>{msg}</div>}
    </div>
  )
}
