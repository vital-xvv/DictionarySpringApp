import {Button, TextField} from "@mui/material";
import {Link as RouterLink, useNavigate} from "react-router-dom";
import './loginPage.scss'
import {useState} from "react";
import useService from "../../../services/useService";

const LoginPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const {process, login, isLoggedIn} = useService();

  const handleLogin = async () => {
    await login(email, password);

    isLoggedIn() ? navigate('/') : null
  }

  return (<div className="login-page">
    <div className="main-heading">Login</div>
    <div className="main-container">
      <TextField variant="outlined" type="email" onChange={(e) => setEmail(e.target.value)} label="Email"/>
      <TextField variant="outlined" type="password" onChange={(e) => setPassword(e.target.value)} label="Password"/>
      <div className="info">Don`t have an account yet? <RouterLink to={'/register'}>Register</RouterLink>
      </div>
      <Button variant="contained" onClick={handleLogin} disabled={process === 'loading'}>Login</Button>
    </div>
  </div>)
}
export default LoginPage;
