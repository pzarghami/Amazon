import { Link, useLocation, useNavigate } from 'react-router-dom';
import './HeaderProfile.css';
import './Dropdown.css';
import axios from 'axios';


export default function HeaderProfile() {
  var isLoggedIn = localStorage.getItem('userLoggedIn');
  var userId = localStorage.getItem('userId');

  const location = useLocation();
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
    <div className="dropdown porfile-dropdown-contianer">
      <span
        id="profileDropdown"
        data-toggle="dropdown"
        className="profile-icon iconify-inline"
        data-icon="healthicons:ui-user-profile-negative"
      ></span>
      <div className="profile-menu dropdown-menu " aria-labelledby="profileDropdown">
        {isLoggedIn ?
          <>
            <span className="menu-item dropdown-item ">{userId}</span>
            <Link className="menu-item dropdown-item " to='/watchlist'>watch list</Link>
            <div
              onClick={handleLogout}
              className="menu-item dropdown-item"
            >
              <span>Logout</span>
            </div>
          </>
          :
          <>
            <Link state={{ from: location }} className="menu-item dropdown-item " to="/login">ورود</Link>
            <Link className="menu-item dropdown-item " to='signup'>ثبت نام</Link>
          </>
        }
      </div>
    </div>
  )
}