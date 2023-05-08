import '../css/auth.css';
import logo from '../../images/logo.png'
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState } from 'react';



export default function Login() {
  const location = useLocation();
  const navigate = useNavigate();

  const [error, setError] = useState('');

  const [username, setUser] = useState('Parmida');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    const from = location.state?.from || '/commodities';
    try {
      localStorage.setItem('userId', username);
      // const data = { username, password };

      // const response = await axios.post('/auth/login/', data);

      // if (response.data.status) {
      //   localStorage.setItem('userLoggedIn', true);
      //   localStorage.setItem('userId', username);
      //   navigate(from, { replace: true });
      // }

    } catch (e) {
      setError('ایمیل یا رمز عبور صحیح نیست')
    }
  }
  return (
      <div className="floating-card">
        <div className="card-body p-md-5 mx-md-4">

          <div className="icon-picture">
            <img src={logo} />
          </div>

          <div className="text-center">
            <h2 className="mt-1 mb-5 pb-1">فرم ورود</h2>
          </div>

          <form onSubmit={handleLogin}>
            <p>لطفا اطلاعات خود را وارد کنید</p>

            <div className="form-outline mb-4">
            <label className="form-label" for="form2Example11">نام کاربری</label>
              <input
                type="username"
                id="form2Example11"
                className="form-control"
                onChange={e => {setUser(e.target.value)}}
                />
            </div>

            <div className="form-outline mb-4">
            <label className="form-label" for="form2Example22">رمز عبور</label>
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
              <button className="btn btn-danger btn-block fa-lg gradient-custom-2 mb-3" type="submit">ورود</button>
            </div>
            <div className="d-flex align-items-center justify-content-center pb-4">
              <div className="mx-3">
                <Link to='/signup'>
                  <button type="button" className="btn btn-outline-danger">ثبت نام</button>
                </Link>
              </div>
              <p className="mb-0 me-2">حساب ندارید؟</p>
            </div>

          </form>

        </div>
      </div>
  )
}