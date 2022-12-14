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
  const [numOfPages, setNumOfPages] = useState(0);
  const {getWords, deleteWord, isLoggedIn, getWordsByFilter} = useService()

  useEffect(() => {
    if (localStorage.getItem('role') !== 'ADMIN') {
      return navigate(isLoggedIn() ? '/' : '/login');
    }
    handleFetchWords(selectedLanguage,currentPage - 1)
  }, [currentPage]);

  useEffect(() => {
    handleSearch()
  }, [searchFilter]);

  const handleSearch = async () => {
    if(!searchFilter) return handleFetchWords()
    const {data} = await getWordsByFilter(searchFilter, selectedLanguage);
    const pages = Math.ceil(data.length / 5)
    setNumOfPages(pages)
    setWords(data)
  }

  const handleFetchWords = async (lang = selectedLanguage, offset = 0) => {
    const {data} = await getWords(lang, offset);
    if(!data.words.length) {
      return setCurrentPage((prev) => prev - 1)
    }
    setWords(data.words)
    setNumOfPages(data.numberOfPages);
  }

  const handleDeleteWord = async (word) => {
    if(!confirm('Delete word?')) return;
    await deleteWord(word, selectedLanguage)
    handleFetchWords(selectedLanguage, currentPage - 1)
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
            <MenuItem value={'ua'}>????????????????????</MenuItem>
            <MenuItem value={'es'}>Espa??ol</MenuItem>
          </Select>
        </div>
        <div><Link to={'add'}><Button variant="contained" color="primary">Add Word</Button></Link></div>
      </div>
      {words
          // .filter(word => word.word.includes(searchFilter) || !searchFilter.length)
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
                            onClick={() => handleDeleteWord(word.word)}
                    >Remove</Button>
                  </Typography>
                </div>
              </CardContent>
            </Card>
          )
      )}
      {!searchFilter ? <div className="arrows-group">
        <Button variant="outlined"
                disabled={currentPage - 1 < 1}
                onClick={() => setCurrentPage((prev) => prev - 1)}
        >&#60;</Button>
        <div>{currentPage}</div>
        <Button variant="outlined"
                disabled={currentPage + 1 > numOfPages}
                onClick={() => setCurrentPage((prev) => prev + 1)}
        >&#62;</Button>
      </div> : null}
    </div>
  </div>);

}

export default adminPage;
