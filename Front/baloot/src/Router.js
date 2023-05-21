import { Navigate, Route, Routes } from "react-router-dom";
import Login from "./pages/js/Login";
import Signup from "./pages/js/Signup"
import NotFound404 from "./pages/js/NotFound404";
import Commodities from "./pages/js/Commodities";
import Layout from "./layout/Layout";
import Commodity from "./pages/js/Commodity";
import  Provider  from "./pages/js/Provider";
import User from "./pages/js/User";


export default function Router() {
  return (
    <Routes>
      <Route path='/' element={<Navigate to='/commodities' />} />
      <Route path='*' element={<NotFound404 />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route element={<Layout />}>
        <Route path='/commodities' element={<Commodities />} />
        <Route path='/commodities/:id' element={<Commodity/>}/>
        <Route path='/provider/:id' element={<Provider/>}/>
        <Route path='/user' element ={<User/>}/>
      </Route>
    </Routes>
  )
}