import {Button, Card, CardContent, MenuItem, Select, TextField, Typography} from "@mui/material";
import './adminPage.scss'
import {Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import useService from "../../../services/useService";

const adminPage = () => {
  const navigate = useNavigate()
  const [selectedLanguage, setSelectedLanguage] = useState('en');
  const [words, setWords] = useState([]);
  const [searchFilter, setSearchFilter] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [numOfPages, setNumOfPages] = useState('');
  const {getWords, deleteWord, isLoggedIn} = useService()

  useEffect(() => {
    if (localStorage.getItem('role') !== 'ADMIN') {
      return navigate(isLoggedIn() ? '/' : '/login');
    }
    handleFetchWords(currentPage - 1)
  }, [currentPage]);

  const handleFetchWords = async (offset = 0) => {
    const {data} = await getWords(selectedLanguage, offset);
    setWords(data.words)
    setNumOfPages(data.numberOfPages);
  }

  return (<div className="admin-page">
    <div className="main-heading">Admin Page</div>
    <div className="admin-container">
      <div className="main-row">
        <div className="input-group">
          <TextField label="Search" variant="outlined" onChange={(e) => setSearchFilter(e.target.value)}/>
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
        <div><Link to={'add'}><Button variant="contained" color="primary">Add Word</Button></Link></div>
      </div>
      {words
          .filter(word => word.word.includes(searchFilter) || !searchFilter.length)
          .map((word, idx) => (
            <Card variant="outlined" key={idx}>
              <CardContent className="card-content">
                <div><Typography variant="h5" component="div">
                  <Button variant="outlined"
                          color="secondary"
                          onClick={() => navigate(`${word.word}`, {state: {lang: selectedLanguage}})}
                  >{word.word}</Button>
                </Typography>
                  <Typography sx={{mb: 1.5}} color="text.secondary">
                    {word.partOfSpeech}
                  </Typography>
                  <Typography variant="body2">
                    {word.meaning}
                  </Typography></div>
                <div className="btn-group">
                  <Typography>
                    <Button variant="outlined"
                            onClick={() => navigate(`edit/${word.word}`, {state: {lang: selectedLanguage}})}
                    >Edit</Button>
                  </Typography>
                  <Typography>
                    <Button variant="outlined" color="error"
                            onClick={() => {
                              deleteWord(word.word, selectedLanguage)
                              handleFetchWords()
                            }}
                    >Remove</Button>
                  </Typography>
                </div>
              </CardContent>
            </Card>
          )
      )}
      <div className="arrows-group">
        <Button variant="outlined"
                disabled={currentPage - 1 < 1}
                onClick={() => setCurrentPage((prev) => prev - 1)}
        >&#60;</Button>
        <div>{currentPage}</div>
        <Button variant="outlined"
                disabled={currentPage + 1 > numOfPages}
                onClick={() => setCurrentPage((prev) => prev + 1)}
        >&#62;</Button>
      </div>
    </div>
  </div>);

}

export default adminPage;
