import { Link, useLocation, useNavigate } from 'react-router-dom';
import './HeaderProfile.css';
import './Dropdown.css';
import axios from 'axios';
import { useEffect, useState } from "react";
import SearchBarHeader from '../component/SearchBarHeader';
import LoginButton from './LoginButton';



export default function HeaderProfile() {
  var isLoggedIn = localStorage.getItem('userLoggedIn');
  var userId = localStorage.getItem('userId');
  const [numOfCartStorage, setStorage]=useState(0);


  const location = useLocation();
  const navigate = useNavigate();




  useEffect(() => {
    console.log("HDHODIHSOFH");
    var numOfcart = localStorage.getItem('numOfCartStorage');
    setStorage(numOfcart);

}, []);
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
              {numOfCartStorage ?
                <button type="button" className='full-cart'>{"Cart  " + numOfCartStorage}</button>
                :
                <button type="button" className='empty-cart'>{"Cart  " + numOfCartStorage}</button>
              }

            </Link>
          </div>
        </div>
      </>

    </>
  )
}