import React from 'react';
import { useNavigate } from 'react-router-dom';
import './UserArea.css';
import { getUserRole } from '../utils/auth';

const UserArea = () => {
  const navigate = useNavigate(); 
  const role = getUserRole();

  const handleSignOut = () => {
    localStorage.removeItem('token');  
    navigate('/signin'); 
  };

  return (
    <div className="user-area-container">
      <h1>Welcome!</h1>
      <p>This is your personal area where you can manage your pets and account settings.</p>
      
      <div className="buttons-container">
        <button onClick={() => navigate('/create-pet')} className="button">
          Create Pet
        </button>
        <button onClick={() => navigate('/my-pets')} className="button">
          My Pets
        </button>
        <button onClick={() => alert('View Profile clicked')} className="button">
          View Profile
        </button>
        <button onClick={handleSignOut} className="button">
          Sign Out
        </button>

        {/* ADMIN-ONLY BUTTONS */}
        {role === 'ROLE_ADMIN' && (
          <>
            <button onClick={() => navigate('/all-users')} className="button admin-button">
              See All Users
            </button>
            <button onClick={() => navigate('/all-pets')} className="button admin-button">
              See All Pets
            </button>
          </>
        )}
      </div>
    </div>
  );

};

export default UserArea;