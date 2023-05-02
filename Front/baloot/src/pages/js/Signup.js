import '../css/auth.css';
import logo from '../../images/logo.png'
import { Link, useLocation, useNavigate } from 'react-router-dom';


export default function Signup() {
    return (
        <div class="floating-card-signup">
            <div class="card-body p-md-5 mx-md-4">

                <div class="icon-picture">
                    <img src={logo} />
                </div>

                <div class="text-center">
                    <h2 class="mt-1 mb-5 pb-1">فرم ثبت نام</h2>
                </div>

                <form>
                    <p><strong>لطفا اطلاعات خود را وارد کنید</strong></p>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example11">نام کاربری</label>
                        <input type="email" id="form2Example11" class="form-control" />

                    </div>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">رمز عبور</label>
                        <input type="password" id="form2Example22" class="form-control" />

                    </div>

                    <div class="form-outline mb-4">
                        <label class="form-label" for="form2Example22">تکرار رمز عبور</label>
                        <input type="password" id="form2Example22" class="form-control" />

                    </div>

                    <div class="text-center pt-1 mb-5 pb-1">
                        <button class="btn btn-danger btn-block fa-lg gradient-custom-2 mb-3" type="button">ثبت نام</button>
                    </div>

                    <div class="d-flex align-items-center justify-content-center pb-4">

                        <div class="mx-3">
                            <Link to='/login'>
                                <button type="button" class="btn btn-outline-danger">ورود</button>
                            </Link>

                        </div>
                        <p class="mb-0 me-2">حساب کاربری دارید؟</p>
                    </div>

                </form>

            </div>
        </div>
    )
}