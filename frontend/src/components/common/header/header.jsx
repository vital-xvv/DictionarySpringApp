import './header.scss'
import {Button} from "@mui/material";
import {Link, useNavigate} from 'react-router-dom';
import {useLocation} from "react-router-dom";
import useService from "../../../services/useService";
import {useEffect, useState} from "react";

const Header = () => {
  const {pathname} = useLocation();
  const {isLoggedIn} = useService();
  const [displayName, setDisplayName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    setDisplayName(localStorage.getItem('displayName'))
  }, []);


  const handleLogout = () => {
    setDisplayName('');
    localStorage.clear();
    navigate('/login')
  }


  return pathname === '/' ? <header className="header">
    <div className="logo">DictionaryApp</div>
    {isLoggedIn() ? <div className="header-btn-group">
      <div><Link to="userInfo">{displayName ? displayName : localStorage.getItem('username')}</Link></div>
      <Button variant="outlined" onClick={handleLogout}>Logout</Button>
    </div> : <Link to="/login"><Button variant="contained" color="secondary">Login</Button></Link>}
  </header> : null
}

export default Header;
