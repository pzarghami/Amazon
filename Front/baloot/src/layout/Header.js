import './Header.css';
import './Dropdown.css';
import HeaderWithoutProfile from './HeaderWithoutProfile';

import logo from '../images/logo.png';
import magnifier from '../images/magnifier.png'
import { Link, useMatch, useLocation } from 'react-router-dom';
import HeaderProfile from './HeaderProfile';


export default function Header() {
  const location = useLocation();
  return (
    <nav class="navbar">
      <div class="title">
        <img src={logo} alt="logo" />
        <span>Baloot</span>
      </div>
      {location.pathname == "/commodities" ?
        <HeaderWithoutProfile />
        :
        <HeaderProfile />
      }


    </nav>
  )
}