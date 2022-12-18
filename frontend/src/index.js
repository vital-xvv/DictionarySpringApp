import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import HomePage from "./components/pages/homePage/homePage";
import LoginPage from "./components/pages/loginPage/loginPage";
import RegisterPage from "./components/pages/registerPage/registerPage";
import AdminPage from "./components/pages/adminPage/adminPage";
import FormPage from "./components/pages/adminPage/components/formPage/formPage";
import WordPage from "./components/pages/adminPage/components/wordPage/wordPage";
import UserInfoPage from "./components/pages/userInfoPage/userInfoPage";


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App/>}>
            <Route index element={<HomePage/>}/>
            <Route path="login" element={<LoginPage/>}/>
            <Route path="register" element={<RegisterPage/>}/>
            <Route path="userInfo" element={<UserInfoPage/>} />
            <Route path="admin" element={<AdminPage/>}/>
            <Route path="admin/:word" element={<WordPage/>}/>
            <Route path="admin/edit/:word" element={<FormPage/>}/>
            <Route path="admin/add" element={<FormPage/>}/>
          </Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>
);

