import {useState} from "react";
import {Button, TextField} from "@mui/material";
import {Link as RouterLink, useNavigate} from "react-router-dom";
import './registerPage.scss'
import useService from "../../../services/useService";

const registerPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repPassword, setRepPassword] = useState('');
    const {register} = useService();
    const navigate = useNavigate();

    const handleRegister = () => {
        if(password !== repPassword) return alert('Passwords don\'t match!');
        register(email, password);
        navigate('/login');
    }

    return (<div className="register-page">
        <div className="main-heading">Register</div>
        <div className="main-container">
            <TextField variant="outlined" type="email" onChange={(e) => setEmail(e.target.value)} label="Email"/>
            <TextField variant="outlined" type="password" onChange={(e) => setPassword(e.target.value)} label="Password"/>
            <TextField variant="outlined" type="password" onChange={(e) => setRepPassword(e.target.value)} label="Repeat Password"/>
            <div className="info">Already have an account? <RouterLink to={'/login'}>Login</RouterLink>
            </div>
            <Button variant="contained" color="secondary" onClick={handleRegister}>Register</Button>
        </div>
    </div>)
}

export default registerPage;
