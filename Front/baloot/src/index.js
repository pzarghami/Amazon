import React from 'react';
import axios from "axios";
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.headers.common["Access-Control-Allow-Origin"] = "GET, PUT, POST";
axios.defaults.headers.common["Access-Control-Allow-Methods"] = "GET, PUT, POST, DELETE, OPTIONS";
axios.defaults.headers.common["Access-Control-Allow-Headers"] = "Origin, X-Requested-With, Content-Type, Accept";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
