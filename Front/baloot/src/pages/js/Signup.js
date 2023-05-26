import '../css/auth.css';
import logo from '../../images/logo.png'
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';


export default function Signup() {
    const location = useLocation();
    const navigate = useNavigate();

    const [error, setError] = useState('');

    const [username, setUser] = useState('');
    const [password, setPassword] = useState('');
    const [passwordAuth, setPasswordAuth] = useState('');
    const [email, setEmail]=useState('');
    const [birthDate, setBithDate]=useState('');
    const [address, setAddress]=useState('');

    const handelSignup = async (e) => {

        e.preventDefault();
        setError('');
        const from = location.state?.from || '/commodities';
        try {
            if(password != passwordAuth){
                setError("Your password is not the same.");
                return;
            }
            const data = { username:username, password:password, email:email, birthDate:birthDate, address:address };
            
            const response = await axios.post('/auth/register/', data);

            if (response.data.status) {
                localStorage.setItem('userLoggedIn', true);
                localStorage.setItem('userId', username);
                navigate(from, { replace: true });
            }

        } catch (e) {
            setError('The username is already taken.')
        }
    }
    return (
        <div class="floating-card-signup">
            <div class="card-body p-md-5 mx-md-4">

                <div class="icon-picture">
                    <img src={logo} />
                </div>

                <div class="text-center">
                    <h2 class="mt-1 mb-5 pb-1">Signup</h2>
                </div>

                <form onSubmit={handelSignup}>
                    <p><strong>Please enter your information</strong></p>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example11">Username</label>
                        <input type="username"
                            id="form2Example11"
                            class="form-control"
                            onChange={e => { setUser(e.target.value) }}
                        />
                    </div>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">Password</label>
                        <input
                            type="password"
                            id="form2Example22"
                            class="form-control"
                            onChange={e => { setPassword(e.target.value) }} />

                    </div>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">Confirm Password</label>
                        <input type="password"
                            id="form2Example22"
                            class="form-control"
                            onChange={e => { setPasswordAuth(e.target.value) }} />

                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">Email</label>
                        <input type="password"
                            id="form2Example22"
                            class="form-control"
                            onChange={e => { setEmail(e.target.value) }} />

                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">Address</label>
                        <input type="password"
                            id="form2Example22"
                            class="form-control"
                            onChange={e => { setAddress(e.target.value) }} />

                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">Address</label>
                        <input type="password"
                            id="form2Example22"
                            class="form-control"
                            onChange={e => { setBithDate(e.target.value) }} />

                    </div>
                    {error &&
                        <div className="text-center pt-1 mb-5 pb-1">
                            <span className='text-danger'>{error}</span>
                        </div>
                    }
                    <div className="text-center pt-1 mb-5 pb-1">
                        <button className="btn btn-danger btn-block fa-lg gradient-custom-2 mb-3" type="submit">Signup</button>
                    </div>

                    <div class="d-flex align-items-center justify-content-center pb-4">

                        <div class="mx-3">
                            <Link to='/login'>
                                <button type="button" class="btn btn-outline-danger">Login</button>
                            </Link>

                        </div>
                        <p class="mb-0 me-2">Have acount?</p>
                    </div>

                </form>

            </div>
        </div>
    )
}