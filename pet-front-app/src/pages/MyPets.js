import React, { useState, useEffect } from 'react';
import api from '../utils/axios';
import { useNavigate } from 'react-router-dom';
import './MyPets.css';

import whiteCat from '../assets/pets/white-cat.png';
import blackCat from '../assets/pets/black-cat.jpg';
import pinkFlamingo from '../assets/pets/pink-flamingo.png';
import greenToucan from '../assets/pets/green-toucan.png';

const petImages = {
  CAT: {
    WHITE: whiteCat,
    BLACK: blackCat,
  },
  FLAMINGO: {
    PINK: pinkFlamingo,
  },
  TOUCAN: {
    GREEN: greenToucan,
  }
};

const MyPets = () => {
  const [pets, setPets] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await api.get('/pets/getAll', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPets(response.data);
      } catch (err) {
        setError('Failed to fetch pets. Please try again later.');
      }
    };

    fetchPets();
  }, []);

  const renderStatBar = (value) => {
    const barWidth = `${value}%`;
    return (
      <div className="stat-bar">
        <div className="stat-fill" style={{ width: barWidth }}></div>
      </div>
    );
  };

  return (
    <div className="my-pets-container">
      <h1>My Pets</h1>

      {error && <p className="error-message">{error}</p>}

      <div className="pets-list">
        {pets.length > 0 ? (
          pets.map((pet) => {
            const petImage = petImages[pet.type]?.[pet.color] || '';

            return (
              <div key={pet.name} className="pet-card" onClick={() => navigate(`/pets/${pet.name}`)}>
                <h3>{pet.name}</h3>
                {petImage && (
                  <img
                    src={petImage}
                    alt={`${pet.color} ${pet.type}`}
                    className="pet-card-image"
                  />
                )}
                <div className="pet-stats">
                  <p>Happiness:</p>
                  {renderStatBar(pet.happiness)}

                  <p>Energy Level:</p>
                  {renderStatBar(pet.energyLevel)}

                  <p>Hunger:</p>
                  {renderStatBar(pet.hunger)}
                </div>
              </div>
            );
          })
        ) : (
          <p>No pets found. Start by creating one!</p>
        )}
      </div>
    </div>
  );
};

export default MyPets;