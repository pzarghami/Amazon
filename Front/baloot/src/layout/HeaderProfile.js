import { Link, useLocation, useNavigate } from 'react-router-dom';
import './HeaderProfile.css';
import './Dropdown.css';
import axios from 'axios';
import { useEffect, useState } from "react";
import SearchBarHeader from '../component/SearchBarHeader';
import LoginButton from './LoginButton';



export default function HeaderProfile(props) {
  const {numOfCart}=props;

  var isLoggedIn = localStorage.getItem('userLoggedIn');
  var userId = localStorage.getItem('userId');
//   useEffect(() => {
//     console.log(numOfcart);

// }, [numOfcart]);



  const location = useLocation();
  const navigate = useNavigate();

  return (
    <>
      {
        location.pathname == "/commodities/" || location.pathname == "/commodities" &&
        <SearchBarHeader />
      }
      <>
        <div class="user-container">

          <div class="user-button">
            <Link to='/user'>
              <button type="button" >{userId}</button>
            </Link>
          </div>

          <div class="user-cart-button">
            <Link to='/user'>
              {numOfCart ?
                <button type="button" className='full-cart'>{"Cart  " + numOfCart}</button>
                :
                <button type="button" className='empty-cart'>{"Cart  " + numOfCart}</button>
              }

            </Link>
          </div>
        </div>
      </>

    </>
  )
}