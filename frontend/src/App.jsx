import './App.scss';
import {Outlet} from "react-router-dom";
import Header from "./components/common/header/header";

function App() {

  return (
      <div className="app">
        <Header/>
        <Outlet/>
      </div>
  );
}

export default App;