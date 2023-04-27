import { Navigate, Route, Routes } from "react-router-dom";
import Layout from "../layouts/Layout";
import Actor from "../pages/Actor";
import Login from "../pages/Login";
import Signup from "../pages/Signup";
import Movie from "../pages/Movie";
import Movies from "../pages/Movies";
import Watchlist from "../pages/Watchlist";
import ProtectedPages from "./ProtectedPages";
import NotFound404 from "../pages/NotFound404";

export default function Router() {
  return (
    <Routes>
      <Route path='/' element={<Navigate to='/commodities' />} />
      <Route path='*' element={<NotFound404 />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route element={<Layout />}>
        <Route path='/movies' element={<Movies />} />
        <Route path='/404' element={<NotFound404 />} />
        <Route path='/movies/:id' element={<Movie />} />
        <Route path='/actors/:id' element={<Actor />} />
        {/* Protected Pages */}
        <Route element={<ProtectedPages />}>
          <Route path='/watchlist' element={<Watchlist />} />
        </Route>
      </Route>
    </Routes>
  )
}