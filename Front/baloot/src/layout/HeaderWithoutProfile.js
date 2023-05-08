import LoginButton from './LoginButton';
import FilteringCommodities from '../component/SearchBarHeader'
import { Link, useMatch, useLocation } from 'react-router-dom';



export default function HeaderWithoutProfile() {
    const location = useLocation();
    return (
        <>
            {location.pathname == "/commodities" &&
                <FilteringCommodities/>
            }
            <LoginButton />
        </>
    )
}