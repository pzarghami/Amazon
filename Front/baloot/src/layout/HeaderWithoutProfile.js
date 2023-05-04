import './HeaderWithoutProfile.css';
import LoginButton from './LoginButton';


import magnifier from '../images/magnifier.png'
import { Link, useMatch, useLocation } from 'react-router-dom';



export default function HeaderWithoutProfile() {
    const location = useLocation();
    return (
        <>
            {location.pathname == "/commodities" &&
                <>
                    <form class="search-box">
                        <select class="category">
                            <option value="name">Name</option>
                            <option value="cat">Category</option>
                        </select>
                        <input type="text" className='search-for' ></input>
                        <img src={magnifier} alt="magnifier" type="submit" />
                    </form>
                </>
            }
            <LoginButton />
        </>
    )
}