import './searchBox.scss';
import {FormControl, MenuItem, Select, TextField} from '@mui/material';
import {useEffect, useState} from 'react';

const SearchBox = ({searchRequest, setWordInfo, setTranslationResult, setSearchRequest}) => {
  const [selectedLanguage, setSelectedLanguage] = useState('');
  const [translateLanguage, setTranslateLanguage] = useState('');

  useEffect(() => {
    const setWord = setTimeout(() => {
      if (searchRequest) {
        setWordInfo(selectedLanguage)
      }
    }, 450)

    return () =>
        clearTimeout(setWord)

  }, [searchRequest, selectedLanguage]);

  return (
      <div className='box'>
        <TextField
            disabled={!selectedLanguage}
            id='standard-basic'
            variant='standard'
            placeholder='Search'
            onChange={(e) => {
              setSearchRequest(e.target.value);
            }}
        />
        <FormControl sx={{m: 1, minWidth: 120}}>
          <Select
              value={selectedLanguage}
              defaultValue={'language'}
              onChange={(e) => {
                setSelectedLanguage(e.target.value)
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
        </FormControl>
        <FormControl sx={{m: 1, minWidth: 120}}>
          <Select
              value={translateLanguage}
              onChange={(e) => {
                setTranslateLanguage(e.target.value);
                setTranslationResult(selectedLanguage, e.target.value);
              }}
              displayEmpty
          >
            <MenuItem value='' disabled>
              <em>Language</em>
            </MenuItem>
            <MenuItem disabled={selectedLanguage === 'en'} value='en'>English</MenuItem>
            <MenuItem disabled={selectedLanguage === 'ua'} value='ua'>Українська</MenuItem>
            <MenuItem disabled={selectedLanguage === 'es'} value='es'>Español</MenuItem>
          </Select>
        </FormControl>
      </div>
  );
};

export default SearchBox;
