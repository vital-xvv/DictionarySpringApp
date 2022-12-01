import {Button, Card, CardActions, CardContent, Typography} from "@mui/material";
import {Link} from "react-router-dom";

const adminPage = () => {
  return (
      <div className="admin-page">
        <div className="main-heading">Admin Page</div>
        <div className="admin-container">
          <Card variant="outlined">
            <CardContent>
              <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                Word of the Day
              </Typography>
              <Typography variant="h5" component="div">
                be
              </Typography>
              <Typography sx={{ mb: 1.5 }} color="text.secondary">
                adjective
              </Typography>
              <Typography variant="body2">
                well meaning and kindly.
                <br />
                {'"a benevolent smile"'}
              </Typography>
            </CardContent>
            <CardActions>
              <Button size="small"><Link to="/aboba">Learn More</Link></Button>
            </CardActions>
          </Card>
          <div>3</div>
        </div>
      </div>);
}

export default adminPage;
