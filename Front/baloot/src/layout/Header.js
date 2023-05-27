import './Header.css';
import './Dropdown.css';
import HeaderWithoutProfile from './HeaderWithoutProfile';

import logo from '../images/logo.png';
import magnifier from '../images/magnifier.png'
import { Link, useMatch, useLocation } from 'react-router-dom';
import HeaderProfile from './HeaderProfile';


export default function Header(props) {
  const {numOfCart}=props;
  const location = useLocation();

  return (
    <nav class="navbar">
      <div class="title">
        <img src={logo} alt="logo" />
        <span>Baloot</span>
      </div>
      {localStorage.getItem('userLoggedIn') ?
        <HeaderProfile numOfCart={numOfCart} />

        :
        <HeaderWithoutProfile />
      }


    </nav>
  )
}