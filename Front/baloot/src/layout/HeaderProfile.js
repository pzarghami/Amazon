import { Link, useLocation, useNavigate } from 'react-router-dom';
import './HeaderProfile.css';
import './Dropdown.css';
import axios from 'axios';
import SearchBarHeader from '../component/SearchBarHeader';
import LoginButton from './LoginButton';
import { useState } from 'react';


export default function HeaderProfile() {
  var isLoggedIn = localStorage.getItem('userLoggedIn');
  var userId = localStorage.getItem('userId');

  const location = useLocation();
  const numOfCartStorage=useState(0);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      localStorage.removeItem('userLoggedIn');
      localStorage.removeItem('userId');
      const response = await axios.post('/auth/logout');
      if (response.data.status) {
        navigate('/movies');
      }
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <>
      {
        location.pathname == "/commodities" &&
        <SearchBarHeader />
      }
      <>
        <div class="user-container">

          <div class="user-button">
            <Link to='/users'>
              <button type="button" >{localStorage.getItem('userId')}</button>
            </Link>
          </div>

          <div class="user-button">
            <Link to='/users'>
              <button type="button" >{"Cart  " + numOfCartStorage[0]}</button>
            </Link>
          </div>
        </div>
      </>

    </>
  )
}