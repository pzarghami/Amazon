import './LoginButton.css';

import { Link, useMatch, useLocation } from 'react-router-dom';



export default function LoginButton() {
    const location = useLocation();
    return (
        <>
            <div class="auth-container">
                
                <div class="auth-button">
                    <Link to='/signup'>
                        <button type="button" >Register</button>
                    </Link>
                </div>

                <div class="auth-button">
                    <Link to='/login'>
                        <button type="button" >Login</button>
                    </Link>
                </div>
            </div>
        </>
    )
}