import {useState} from "react";
import {Button, TextField} from "@mui/material";
import {Link as RouterLink} from "react-router-dom";
import './registerPage.scss'

const registerPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    return (<div className="register-page">
        <div className="main-heading">Register</div>
        <div className="main-container">
            <TextField variant="outlined" type="email" onChange={(e) => setEmail(e.target.value)} label="Email"/>
            <TextField variant="outlined" type="password" onChange={(e) => setPassword(e.target.value)} label="Password"/>
            <div className="info">Already have an account? <RouterLink to={'/login'}>Login</RouterLink>
            </div>
            <Button variant="contained" color="secondary">Register</Button>
        </div>
    </div>)
}

export default registerPage;
