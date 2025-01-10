import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/axios';
import './CreatePet.css';

const petTypes = ['CAT', 'CAPYBARA', 'FLAMINGO', 'SHEEP', 'TOUCAN', 'RABBIT'];
const petColors = ['WHITE', 'BLACK', 'PINK', 'GREEN'];

const CreatePet = () => {
    const [name, setName] = useState('');
    const [type, setType] = useState(petTypes[0]); 
    const [color, setColor] = useState(petColors[0]); 
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
          const token = localStorage.getItem('token');
          
          const requestBody = {
            name,
            type,
            color,
          };
    
          const response = await api.post('/pets/new', requestBody, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });

          const createdPetName = response.data.name;
          setSuccess('Pet created successfully!');
          navigate(`/pets/${createdPetName}`);  
    } catch (error) {
        console.error('Error response:', error.response);
        setError(error.response?.data?.message || 'Failed to create pet. Please try again.');
    }
  };

  return (
    <div className="create-pet-container">
      <h1>Create Your Pet</h1>
      
      <form onSubmit={handleSubmit} className="create-pet-form">
        {error && <p className="error-message">{error}</p>}
        {success && <p className="success-message">{success}</p>}

        <input
          type="text"
          placeholder="Pet Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          className="input-field"
        />

        <select
          value={type}
          onChange={(e) => setType(e.target.value)}
          required
          className="select-field"
        >
          <option value="" disabled>Select Pet Type</option>
          {petTypes.map((t) => (
            <option key={t} value={t}>{t}</option>
          ))}
        </select>

        <select
          value={color}
          onChange={(e) => setColor(e.target.value)}
          required
          className="select-field"
        >
          <option value="" disabled>Select Pet Color</option>
          {petColors.map((c) => (
            <option key={c} value={c}>{c}</option>
          ))}
        </select>

        <button type="submit" className="create-btn">Create Pet</button>
      </form>

      <p className="redirect-link">
        Want to return? <a href="/user-area">Back to User Area</a>
      </p>
    </div>
    
  );
};
  
  export default CreatePet;