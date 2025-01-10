import React, { useState, useEffect } from 'react';
import api from '../utils/axios';
import './AllUsers.css';

const AllUsers = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await api.get('/admin/users', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUsers(response.data);
      } catch (err) {
        setError('Failed to fetch users.');
      }
    };

    fetchUsers();
  }, []);

  if (error) {
    return <p className="error">{error}</p>;
  }

  return (
    <div className="all-users-container">
      <h1>All Users</h1>
      <div className="all-users-list">
        {users.map((user) => (
          <div className="user-card" key={user.id}>
            <h3>{user.username}</h3>
            <p className="user-email">{user.email}</p>
            <p className={`user-role ${user.role === 'ADMIN' ? 'admin-role' : ''}`}>
              {user.role === 'ADMIN' ? 'Administrator' : 'User'}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AllUsers;