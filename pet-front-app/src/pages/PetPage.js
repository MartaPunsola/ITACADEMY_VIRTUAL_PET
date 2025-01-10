import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../utils/axios'; 
import './PetPage.css';

// Import pet images
import whiteCat from '../assets/pets/white-cat.png';
import blackCat from '../assets/pets/black-cat.jpg';
import pinkFlamingo from '../assets/pets/pink-flamingo-default.jpg';
import greenToucan from '../assets/pets/green-toucan.png';

// Location-specific images
import forestWhiteCat from '../assets/pets/black-cat-forest.jpg';
import poolWhiteCat from '../assets/pets/black-cat-pool.jpg'; 
import forestBlackCat from '../assets/pets/black-cat-forest.jpg';
import poolBlackCat from '../assets/pets/black-cat-pool.jpg'; 
import forestPinkFlamingo from '../assets/pets/forest-pink-flamingo.jpg'; 
import poolPinkFlamingo from '../assets/pets/pool-pink-flamingo.jpg'; 
import forestGreenToucan from '../assets/pets/black-cat-forest.jpg'; 
import poolGreenToucan from '../assets/pets/black-cat-pool.jpg';

// State changes
import feedPinkFlamingo from '../assets/pets/feed-flamingo.jpg';
import sleepPinkFlamingo from '../assets/pets/sleep-flamingo.png';
import playPinkFlamingo from '../assets/pets/play-flamingo.jpg'; 

// Accessories images
import sunglassesFlamingo from '../assets/pets/sunglasses-flamingo.jpg';
import balloonsFlamingo from '../assets/pets/balloons-flamingo.jpg';

const petImages = {
  CAT: {
    HOME: {
      WHITE: whiteCat,
      BLACK: blackCat,
    },
    FOREST: {
      WHITE: forestWhiteCat,
      BLACK: forestBlackCat,
    },
    SWIMMING_POOL: {
      WHITE: poolWhiteCat,
      BLACK: poolBlackCat,
    },
  },
  FLAMINGO: {
    HOME: {
      PINK: pinkFlamingo,
    },
    FOREST: {
      PINK: forestPinkFlamingo,
    },
    SWIMMING_POOL: {
      PINK: poolPinkFlamingo,
    },
    FEED: {
      PINK: feedPinkFlamingo,
    },
    SLEEP: {
      PINK: sleepPinkFlamingo,
    },
    PLAY: {
      PINK: playPinkFlamingo,
    },
    ACCESSORIES: {
      SUNGLASSES: sunglassesFlamingo,
      BALLOONS: balloonsFlamingo,
    },
    
  },
  TOUCAN: {
    HOME: {
      GREEN: greenToucan,
    },
    FOREST: {
      GREEN: forestGreenToucan,
    },
    SWIMMING_POOL: {
      GREEN: poolGreenToucan,
    },
    
  },
  
};

const accessoryOptions = ['SUNGLASSES', 'BALLOONS'];

const PetPage = () => {
    const { name } = useParams();
    const navigate = useNavigate();
    const [pet, setPet] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const petLocations = ['HOME', 'FOREST', 'SWIMMING_POOL'];
    const [selectedLocation, setSelectedLocation] = useState('HOME');

    const [interaction, setInteraction] = useState('NONE');

    const [selectedAccessory, setSelectedAccessory] = useState('');
  
  useEffect(() => {
    const fetchPet = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await api.get(`/pets/getOne?name=${name}`, { 
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setPet(response.data);
        setSelectedLocation(response.data.location || 'HOME');
      } catch (err) {
        setError('Failed to fetch pet details.');
      }
    };

    fetchPet();
  }, [name]);

  const handleAccessoryChange = async (action) => {
    try {
      const token = localStorage.getItem('token');
      const response = await api.put('/pets/update', {
        name: pet.name,
        petInteraction: 'NONE',
        accessory: action === 'REMOVE' ? null : selectedAccessory,
        location: selectedLocation,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      
      setPet({
        ...response.data,
        accessories: action === 'REMOVE' ? [] : [selectedAccessory], 
      });
      alert(`Accessory ${action === 'REMOVE' ? 'removed' : 'added'} successfully.`);
    } catch (err) {
      alert('Failed to update accessory.');
    }
  };

  const handleLocationChange = async (event) => {
    const newLocation = event.target.value;
    setSelectedLocation(newLocation);
    try {
      const token = localStorage.getItem('token');
      const response = await api.put('/pets/update', {
        name: pet.name,
        petInteraction: 'NONE',
        accessory: pet.accessories?.[0] || null,
        location: newLocation,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setPet(response.data);
    } catch (err) {
      alert('Failed to update location.');
    }
  };

  const handleInteraction = async (action) => {
    try {
      const token = localStorage.getItem('token');
      const response = await api.put('/pets/update', {
        name: pet.name,
        petInteraction: action,
        accessory: pet.accessories?.[0] || null,
        location: pet.location,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setPet(response.data);
      setInteraction(action);
    } catch (err) {
      alert(`Failed to ${action.toLowerCase()} pet.`);
    }
  };

  const handleDeletePet = async () => {
    if (!window.confirm(`Are you sure you want to delete ${pet.name}?`)) return;

    try {
      setLoading(true);
      const token = localStorage.getItem('token');
      await api.delete('/pets/delete', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        data: {
          name: pet.name,
        },
      });
      alert('Pet successfully deleted.');
      navigate('/my-pets'); 
    } catch (err) {
      alert('Failed to delete pet.');
    } finally {
      setLoading(false);
    }
  };

  if (error) {
    return <p className="error">{error}</p>;
  }

  if (!pet) {
    return <p className="loading">Loading pet details...</p>;
  }                
    
  const petImage = interaction !== 'NONE' 
  ? petImages[pet.type]?.[interaction]?.[pet.color]
  : pet.accessories?.length > 0 
    ? petImages[pet.type]?.ACCESSORIES?.[pet.accessories[0]]
    : petImages[pet.type]?.[selectedLocation]?.[pet.color] 
      || petImages[pet.type]?.FOREST?.PINK
      || '';

                    
  const calculatePercentage = (value) => Math.min(100, (value / 100) * 100);

  return (
    <div className="pet-page-container">
  <div className="pet-info">
    <h1>{pet.name}</h1>
    {petImage && <img src={petImage} alt={`${pet.color} ${pet.type}`} className="pet-image" />}
    
    <div className="stats">
      <div className="stat">
        <label>Happiness</label>
        <div className="progress-bar">
          <div 
            className="progress" 
            style={{ width: `${calculatePercentage(pet.happiness)}%` }} />
        </div>
      </div>

      <div className="stat">
        <label>Energy Level</label>
        <div className="progress-bar">
          <div 
            className="progress" 
            style={{ width: `${calculatePercentage(pet.energyLevel)}%` }} />
        </div>
      </div>

      <div className="stat">
        <label>Hunger</label>
        <div className="progress-bar">
          <div 
            className="progress" 
            style={{ width: `${calculatePercentage(pet.hunger)}%`  }} />
        </div>
      </div>
    </div>
  </div>

  <div className="pet-interactions">
    <button onClick={() => handleInteraction('FEED')} className="feed-button">Feed</button>
    <button onClick={() => handleInteraction('SLEEP')} className="sleep-button">Sleep</button>
    <button onClick={() => handleInteraction('PLAY')} className="play-button">Play</button>

    <div className="location-selector">
      <label className="location-label">Change Location:</label>
      <select
        value={selectedLocation}
        onChange={handleLocationChange}
        className="select-location"
        
      >
        <option value="">Select</option>
        {petLocations.map((loc) => (
          <option key={loc} value={loc}>{loc.replace('_', ' ')}</option>
        ))}
      </select>
    </div>

    <div className="accessory-selector">
      <label>Accessories:</label>
      <select value={selectedAccessory} onChange={(e) => setSelectedAccessory(e.target.value)}>
        <option value="">Select</option>
        {accessoryOptions.map((acc) => (
          <option key={acc} value={acc}>{acc}</option>
        ))}
      </select>
      <div className="accessory-buttons">
        <button onClick={() => handleAccessoryChange('ADD')} className="add-button">Add</button>
        <button onClick={() => handleAccessoryChange('REMOVE')} className="remove-button">Remove</button>
      </div>
    </div>

    <button 
      onClick={handleDeletePet} 
      className="delete-button"
      disabled={loading}>
      {loading ? 'Deleting...' : 'Delete Pet'}
    </button>
  </div>
</div>
  );
};


export default PetPage;