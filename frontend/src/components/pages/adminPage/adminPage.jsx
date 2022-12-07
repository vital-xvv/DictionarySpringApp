import {Button, Card, CardContent, TextField, Typography} from "@mui/material";
// import {Link} from "react-router-dom";
import './adminPage.scss'

const adminPage = () => {
  return (<div className="admin-page">
    <div className="main-heading">Admin Page</div>
    <div className="admin-container">
      <div className="main-row">
        <div><TextField label="Search" variant="outlined"/></div>
        <div><Button variant="contained" color="primary">Add Word</Button></div>
      </div>
      <Card variant="outlined">
        <CardContent className="card-content">
          <div><Typography variant="h5" component="div">
            be
          </Typography>
            <Typography sx={{mb: 1.5}} color="text.secondary">
              adjective
            </Typography>
            <Typography variant="body2">
              well meaning and kindly.
              <br/>
              {'"a benevolent smile"'}
            </Typography></div>
          <div className="btn-group">
            <Typography><Button variant="outlined">Edit</Button></Typography>
            <Typography><Button variant="outlined" color="error">Remove</Button></Typography>
          </div>
        </CardContent>
      </Card>
    </div>
  </div>);

}

export default adminPage;
