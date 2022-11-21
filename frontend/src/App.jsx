import './App.scss';
import {Outlet, useNavigate} from "react-router-dom";
import Header from "./components/common/header/header";
import {useEffect} from "react";

function App() {

  let navigate = useNavigate();
  const usrToken = localStorage.getItem('token');

  useEffect(() => {
    if (usrToken === null) {
      navigate('/login');
    }
  }, []);

  return (
      <div className="app">
        <Header/>
        <Outlet/>
      </div>
  );
}

export default App;