import {Button, TextField} from "@mui/material";
import {Link as RouterLink} from "react-router-dom";
import './loginPage.scss'
import {useState} from "react";

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  return (<div className="login-page">
    <div className="main-heading">Login</div>
    <div className="main-container">
      <TextField variant="outlined" type="email" onChange={(e) => setEmail(e.target.value)} label="Email"/>
      <TextField variant="outlined" type="password" onChange={(e) => setPassword(e.target.value)} label="Password"/>
      <div className="info">Don`t have an account yet? <RouterLink to={'/register'}>Register</RouterLink>
      </div>
      <Button variant="contained">Login</Button>
    </div>
  </div>)
}
export default LoginPage;
