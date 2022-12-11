import {useState} from "react";
import {Button, TextField} from "@mui/material";
import {Link as RouterLink, useNavigate} from "react-router-dom";
import './registerPage.scss'
import useService from "../../../services/useService";

const registerPage = () => {
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [password, setPassword] = useState('');
    const [repPassword, setRepPassword] = useState('');
    const {register} = useService();
    const navigate = useNavigate();

    const handleRegister = () => {
        if(password !== repPassword) return alert('Passwords don\'t match!');
        register(username, firstName, lastName, password);
        navigate('/login');
    }

    return (<div className="register-page">
        <div className="main-heading">Register</div>
        <div className="main-container">
            <TextField variant="outlined" type="text" onChange={(e) => setUsername(e.target.value)} label="Username"/>
            <TextField variant="outlined" type="text" onChange={(e) => setFirstName(e.target.value)} label="First Name"/>
            <TextField variant="outlined" type="text" onChange={(e) => setLastName(e.target.value)} label="Last Name"/>
            <TextField variant="outlined" type="password" onChange={(e) => setPassword(e.target.value)} label="Password"/>
            <TextField variant="outlined" type="password" onChange={(e) => setRepPassword(e.target.value)} label="Repeat Password"/>
            <div className="info">Already have an account? <RouterLink to={'/login'}>Login</RouterLink>
            </div>
            <Button variant="contained" color="secondary" onClick={handleRegister}>Register</Button>
        </div>
    </div>)
}

export default registerPage;
