import './header.scss'
import {Button} from "@mui/material";
import {Link} from 'react-router-dom';
import {useLocation} from "react-router-dom";
import Service from "../../../services/service";

const Header = () => {
  const {pathname} = useLocation();
  const service = new Service()
  return pathname === '/' ? <header className="header">
    <div className="logo">DictionaryApp</div>
    {service.isLoggedIn() ? <div className="header-btn-group">
      <div>UserName</div>
      <Button variant="outlined">Logout</Button>
    </div> : <Link to="/login"><Button variant="contained" color="secondary">Login</Button></Link>}
  </header> : null
}

export default Header;
