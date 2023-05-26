import LoginButton from './LoginButton';
import SearchBarHeader from '../component/SearchBarHeader'
import { Link, useMatch, useLocation } from 'react-router-dom';



export default function HeaderWithoutProfile() {
    const location = useLocation();
    return (
        <>
            {location.pathname == "/commodities/" &&
                <SearchBarHeader/>
            }
            <LoginButton />
        </>
    )
}