import { Navigate, Route, Routes } from "react-router-dom";
import React, { useState } from "react";
import axios from "axios";
import Login from "./pages/js/Login";
import Signup from "./pages/js/Signup"
import NotFound404 from "./pages/js/NotFound404";
import Commodities from "./pages/js/Commodities";
import Layout from "./layout/Layout";
import Commodity from "./pages/js/Commodity";
import Provider from "./pages/js/Provider";
import User from "./pages/js/User";
import ProtectedPages from "./component/ProtectedPages";
import UnprotectedPages from "./component/UnprotectedPages";
import Callback from "./pages/js/Callback";
import Request from "./functions/Request";

export default function Router() {
  const [numOfCart, setCart] = useState(0);
  const setNumOfCart = async () => {
    try {

      const response = await Request.post('buyListSize');
      console.log(response);
      if (response.data.status) {
        console.log(response);
        setCart(response.data.content);
      }


    } catch (e) {

    }
  }
  return (
    <Routes>
      <Route path='/' element={<Navigate to='/commodities' />} />
      <Route path='*' element={<NotFound404 />} />
      <Route element={< UnprotectedPages />}>
        <Route path='/login' element={<Login />} />
        <Route path='/signup' element={<Signup />} />
        <Route path='/callback' element={<Callback />} />
      </Route>
      <Route element={<ProtectedPages />}>
        <Route element={<Layout numOfCart={numOfCart} />}>
          <Route path='/commodities' element={<Commodities setNumOfCart={setNumOfCart} />} />
          <Route path='/commodities/:id' element={<Commodity setNumOfCart={setNumOfCart} />} />
          <Route path='/provider/:providerId' element={<Provider />} />
          <Route path='/user' element={<User setNumOfCart={setNumOfCart} />} />
        </Route>
      </Route>
    </Routes>
  )
}