import {Button, Card, CardContent, MenuItem, Select, TextField, Typography} from "@mui/material";
import './adminPage.scss'
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import useService from "../../../services/useService";

const adminPage = () => {
  const navigate = useNavigate()
  const [selectedLanguage, setSelectedLanguage] = useState('en');
  const [words, setWords] = useState([]);
  const {getWords} = useService()
  useEffect(() => {
    if (localStorage.getItem('role') !== 'ROLE_ADMIN') {
      return navigate('/login')
    }
    handleFetchWords()
  }, []);

  const handleFetchWords = async (val = null) => {
    const res = await getWords(val ? val : selectedLanguage);
    setWords(res.words)
  }

  return (<div className="admin-page">
    <div className="main-heading">Admin Page</div>
    <div className="admin-container">
      <div className="main-row">
        <div className="input-group">
          <TextField label="Search" variant="outlined"/>
          <Select
              value={selectedLanguage}
              defaultValue={'language'}
              onChange={(e) => {
                setSelectedLanguage(e.target.value)
                handleFetchWords(e.target.value);
              }}
              displayEmpty
          >
            <MenuItem value='' disabled>
              <em>Language</em>
            </MenuItem>
            <MenuItem value={'en'}>English</MenuItem>
            <MenuItem value={'ua'}>Українська</MenuItem>
            <MenuItem value={'es'}>Español</MenuItem>
          </Select>
        </div>
        <div><Button variant="contained" color="primary">Add Word</Button></div>
      </div>
      {words.map((word, idx) => (
        <Card variant="outlined" key={idx}>
          <CardContent className="card-content">
            <div><Typography variant="h5" component="div">
              {word.word}
            </Typography>
              <Typography sx={{mb: 1.5}} color="text.secondary">
                {word.partOfSpeech}
              </Typography>
              <Typography variant="body2">
                {word.meaning}
              </Typography></div>
            <div className="btn-group">
              <Typography><Button variant="outlined">Edit</Button></Typography>
              <Typography><Button variant="outlined" color="error">Remove</Button></Typography>
            </div>
          </CardContent>
        </Card>)
      )}
    </div>
  </div>);

}

export default adminPage;

/*
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
</Card>*/
