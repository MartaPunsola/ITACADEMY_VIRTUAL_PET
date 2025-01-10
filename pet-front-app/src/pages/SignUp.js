import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/axios';
import './SignUp.css';

const SignUp = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); 
  
    const handleSubmit = async (e) => {
      e.preventDefault();
      try {
        const requestBody = {
          username,
          email,
          password,
        };
  
        const response = await api.post('/auth/signup', requestBody);
        console.log('Sign up successful:', response.data);
        
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('username', response.data.username); 
        navigate('/user-area');

      } catch (error) {
        console.error('Error during signup:', error);
        setError('Sign Up failed, please try again!');
      }
    };

    return (
      <div className="sign-up-container">
        <h1 className="title">Sign Up</h1>
        <form onSubmit={handleSubmit} className="form">
          {error && <p className="error">{error}</p>}
          <input
            type="text"
            placeholder="Enter your username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            className="input-field"
          />
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
          <button type="submit" className="submit-btn">Sign Up</button>
        </form>
        <p className="redirect-link">
          Already have an account? <a href="/signin">Sign In</a>
        </p>
      </div>
    );
  
  };
  
  export default SignUp;