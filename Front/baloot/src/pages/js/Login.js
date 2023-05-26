import '../css/auth.css';
import logo from '../../images/logo.png'
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';



export default function Login() {
  const location = useLocation();
  const navigate = useNavigate();

  const [error, setError] = useState('');

  const [username, setUser] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    const from = location.state?.from || '/commodities';
    try {
      const data = { username: username ,password: password };

      const response = await axios.post('/auth/login/', data);
      console.log(response)
      if (response.data.status) {
        localStorage.setItem('userLoggedIn', true);
        localStorage.setItem('userId', username);
        navigate(from, { replace: true });
      }

    } catch (e) {
      setError("The username or password doesn't correct.")
    }
  }
  return (
    <div className="floating-card">
      <div className="card-body p-md-5 mx-md-4">

        <div className="icon-picture">
          <img src={logo} />
        </div>

        <div className="text-center">
          <h2 className="mt-1 mb-5 pb-1">Login</h2>
        </div>

        <form onSubmit={handleLogin}>
          <p><strong>Please enter your information</strong></p>
          <div className="form-outline mb-4">
            <label className="form-label" for="form2Example11">Username</label>
            <input
              type="username"
              id="form2Example11"
              className="form-control"
              onChange={e => { setUser(e.target.value) }}
            />
          </div>

          <div className="form-outline mb-4">
            <label className="form-label" for="form2Example22">Password</label>
            <input
              type="password"
              id="form2Example22"
              className="form-control"
              onChange={e => { setPassword(e.target.value) }}
            />

          </div>
          {error &&
            <div className="text-center pt-1 mb-5 pb-1">
              <span className='text-danger'>{error}</span>
            </div>
          }
          <div className="text-center pt-1 mb-5 pb-1">
            <button className="btn btn-danger btn-block fa-lg gradient-custom-2 mb-3" type="submit">Login</button>
          </div>
          <div className="d-flex align-items-center justify-content-center pb-4">
            <div className="mx-3">
              <Link to='/signup'>
                <button type="button" className="btn btn-outline-danger">Signup</button>
              </Link>
            </div>
            <p className="mb-0 me-2">Dont have acount?</p>
          </div>

        </form>

      </div>
    </div>
  )
}