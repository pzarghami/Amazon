import { Navigate, Route, Routes } from "react-router-dom";
import Login from "./pages/js/Login";
import Signup from "./pages/js/Signup"
import NotFound404 from "./pages/js/NotFound404";
import Commodities from "./pages/js/Commodities";


export default function Router() {
  return (
    <Routes>
      <Route path='/' element={<Navigate to='/commodities' />} />
      <Route path='/login' element={<Login />} />
      <Route path='*' element={<NotFound404 />} />
      <Route path='/signup' element={<Signup />} />
      <Route path='/commodities' element={<Commodities />} />
    </Routes>
  )
}