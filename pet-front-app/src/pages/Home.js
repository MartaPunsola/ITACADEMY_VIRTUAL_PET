import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home = () => {
  return (
    <div className="home">
      <h1>Welcome to Animalia!</h1>
      <p>Come in and start playing with your virtual pets!</p>

      <div className="buttons">
        {/* Sign Up and Sign In buttons */}
        <Link to="/signup">
          <button className="home-btn">Sign Up</button>
        </Link>
        <Link to="/signin">
          <button className="home-btn">Sign In</button>
        </Link>
      </div>
    </div>
  );
};

export default Home;