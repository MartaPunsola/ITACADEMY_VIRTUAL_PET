import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/axios';
import './SignIn.css';

const SignIn = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const requestBody = {
          email,
          password,
        };
  
        const response = await api.post('/auth/signin', requestBody);
        console.log('Sign in successful:', response.data);
        
      
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('username', response.data.username); 
        navigate('/user-area');


    } catch (error) {
        console.error('Error during signin:', error);
        setError('Sign In failed, please check your credentials!');
      }
    };

    return (
      <div className="sign-in-container">
        <h1 className="title">Sign In</h1>
        <form onSubmit={handleSubmit} className="form">
          {error && <p className="error">{error}</p>}
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="input-field"
          />
          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="input-field"
          />
          <button type="submit" className="submit-btn">Sign In</button>
        </form>
        <p className="redirect-link">
          Don't have an account? <a href="/signup">Sign Up</a>
        </p>
      </div>
    );
  
  };
  
  export default SignIn;

