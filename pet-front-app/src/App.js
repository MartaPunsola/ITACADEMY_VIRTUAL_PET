import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import AdminRoute from './components/AdminRoute';
import Home from './pages/Home'; 
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import UserArea from './pages/UserArea';
import CreatePet from './pages/CreatePet';
import PetPage from './pages/PetPage';
import MyPets from './pages/MyPets';
import AllUsers from './pages/AllUsers';
import AllPets from './pages/AllPets';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} /> {/* Add route for Home page */}
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/user-area" element={
    <ProtectedRoute>
      <UserArea />
    </ProtectedRoute>
  } />
        <Route path="/create-pet" element={
    <ProtectedRoute>
      <CreatePet />
    </ProtectedRoute>
  } />
        <Route path="/pets/:name" element={
    <ProtectedRoute>
      <PetPage />
    </ProtectedRoute>
  } />
        <Route path="/my-pets" element={<ProtectedRoute><MyPets /></ProtectedRoute>} />
        <Route path="/all-users" element={<AdminRoute><AllUsers /></AdminRoute>} />
        <Route path="/all-pets" element={<AdminRoute><AllPets /></AdminRoute>} />
      </Routes>
    </Router>
  );
};

export default App;
