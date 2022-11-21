import './header.scss'
import {Button} from "@mui/material";
import {Link, useNavigate} from 'react-router-dom';
import {useLocation} from "react-router-dom";
import useService from "../../../services/useService";

const Header = () => {
  const {pathname} = useLocation();
  const {isLoggedIn} = useService();
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('token');
    // localStorage.removeItem('username');
    navigate('/login')
  }


  return pathname === '/' ? <header className="header">
    <div className="logo">DictionaryApp</div>
    {isLoggedIn() ? <div className="header-btn-group">
      <div>{localStorage.getItem('username')}</div>
      <Button variant="outlined" onClick={handleLogout}>Logout</Button>
    </div> : <Link to="/login"><Button variant="contained" color="secondary">Login</Button></Link>}
  </header> : null
}

export default Header;
