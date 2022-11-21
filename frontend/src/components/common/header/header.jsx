import './header.scss'
import {Button} from "@mui/material";
import {Link} from 'react-router-dom';
import {useLocation} from "react-router-dom";
import useService from "../../../services/useService";

const Header = () => {
  const {pathname} = useLocation();
  const {isLoggedIn} = useService();
  return pathname === '/' ? <header className="header">
    <div className="logo">DictionaryApp</div>
    {isLoggedIn() ? <div className="header-btn-group">
      <div>UserName</div>
      <Button variant="outlined">Logout</Button>
    </div> : <Link to="/login"><Button variant="contained" color="secondary">Login</Button></Link>}
  </header> : null
}

export default Header;
