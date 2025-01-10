import React, { useState, useEffect } from 'react';
import api from '../utils/axios';
import './AllPets.css';

import whiteCat from '../assets/pets/white-cat.png';
import blackCat from '../assets/pets/black-cat.jpg';
import pinkFlamingo from '../assets/pets/pink-flamingo.png';
import greenToucan from '../assets/pets/green-toucan.png';
import blackCapybara from '../assets/pets/black-capybara.png';

const petImages = {
  CAT: { WHITE: whiteCat, BLACK: blackCat },
  FLAMINGO: { PINK: pinkFlamingo },
  TOUCAN: { GREEN: greenToucan },
  CAPYBARA: { BLACK: blackCapybara },
};

const calculatePercentage = (value) => Math.min(100, (value / 100) * 100);

const AllPets = () => {
  const [pets, setPets] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await api.get('/pets/admin/getAllPets', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPets(response.data);
      } catch (err) {
        setError('Failed to fetch pets.');
      }
    };

    fetchPets();
  }, []);

  if (error) {
    return <p className="error">{error}</p>;
  }

  return (
    <div className="all-pets-container">
      <h1>All Pets</h1>
      <div className="all-pets-list">
        {pets.map((pet) => {
          const petImage = petImages[pet.type]?.[pet.color] || '';
          return (
            <div className="all-pet-card" key={pet.id}>
              <h3>{pet.name}</h3>
              {petImage && <img src={petImage} alt={`${pet.color} ${pet.type}`} />}
              <div className="all-pet-stats">
                <div className="stat">
                  <p className="stat-label">Happiness</p>
                  <div className="stat-bar">
                    <div
                      className="stat-fill"
                      style={{ width: `${calculatePercentage(pet.happiness)}%` }}
                    ></div>
                  </div>
                </div>

                <div className="stat">
                  <p className="stat-label">Energy</p>
                  <div className="stat-bar">
                    <div
                      className="stat-fill"
                      style={{ width: `${calculatePercentage(pet.energyLevel)}%` }}
                    ></div>
                  </div>
                </div>

                <div className="stat">
                  <p className="stat-label">Hunger</p>
                  <div className="stat-bar">
                    <div
                      className="stat-fill"
                      style={{
                        width: `${calculatePercentage(pet.hunger)}%`                    
                      }}
                    ></div>
                  </div>
                </div>
              </div>
              <p className="all-pet-owner">Owner: {pet.username || 'Unknown'}</p>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default AllPets;