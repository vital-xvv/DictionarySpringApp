import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import HomePage from "./components/pages/homePage/homePage";
import LoginPage from "./components/pages/loginPage/loginPage";
import RegisterPage from "./components/pages/registerPage/registerPage";
import AdminPage from "./components/pages/adminPage/adminPage";
import FormPage from "./components/pages/adminPage/components/formPage/formPage";


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App/>}>
            <Route index element={<HomePage/>}/>
            <Route path="login" element={<LoginPage/>}/>
            <Route path="register" element={<RegisterPage/>}/>
            <Route path="admin" element={<AdminPage/>}/>
            <Route path="admin/:word" element={<FormPage/>}/>
            <Route path="admin/add" element={<FormPage/>}/>
          </Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
);

