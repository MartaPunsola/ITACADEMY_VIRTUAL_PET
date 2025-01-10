import React from 'react';
import { Navigate } from 'react-router-dom';
import { getUserRole } from '../utils/auth';  

const AdminRoute = ({ children }) => {
  const role = getUserRole();  
  console.log('Role from token:', role); 

  return role === 'ROLE_ADMIN' ? children : <Navigate to="/user-area" />;
};

export default AdminRoute;