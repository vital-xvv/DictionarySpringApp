import {useLocation, useParams} from "react-router-dom";
import {Button, MenuItem, Select, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import './form.scss'
import useService from "../../../../../services/useService";

const Form = () => {
  const params = useParams()
  const {state} = useLocation()
  const {addWord, changeWord, getWord} = useService()
  const inEditMode = !!params.word
  const [word, setWord] = useState('');
  const [transcription, setTranscription] = useState('');
  const [partOfSpeech, setPartOfSpeech] = useState('');
  const [meaning, setMeaning] = useState('');
  const [example, setExample] = useState('');
  const [synonyms, setSynonyms] = useState('');
  const [langCode, setLangCode] = useState('');

  const handleFetchWord = async () => {
    return await getWord(state.lang, params.word);
  }

  useEffect(() => {
    if(inEditMode) {
      (async function () {
        const res = await handleFetchWord()
        setWord(res.word);
        setTranscription(res.transcription);
        setPartOfSpeech(res.partOfSpeech);
        setMeaning(res.meaning);
        setExample(res.example);
        setSynonyms(res.synonyms);
        setLangCode(state.lang)
      })();
    }
  }, []);



  const handleSubmit = (e) => {
    e.preventDefault()
    const payload = {
      word,
      langCode,
      wordObject: inEditMode ? null : [{
        word,
        transcription,
        partOfSpeech,
        meaning,
        example,
        synonyms
      }]
    }
    inEditMode ? changeWord(payload) : addWord(payload)
  }


  return <div className="word-form">
    <div className="main-heading">{inEditMode ? 'Edit Word' : 'Add Word'}</div>
    <form className="admin-container" onSubmit={handleSubmit}>
      <TextField
          variant="outlined"
          required
          value={word}
          label='Word'
          onChange={(e) => {
            setWord(e.target.value);
          }}
      />
      <TextField
          variant="outlined"
          label='Transcription'
          value={transcription}
          onChange={(e) => {
            setTranscription(e.target.value);
          }}
      />
      <TextField
          variant="outlined"
          label='Part of Speech'
          value={partOfSpeech}
          required
          onChange={(e) => {
            setPartOfSpeech(e.target.value);
          }}
      />
      <TextField
          variant="outlined"
          multiline
          style={{gridColumn: "1 / span 3"}}
          maxRows={2}
          label='Meaning'
          value={meaning}
          required
          onChange={(e) => {
            setMeaning(e.target.value);
          }}
      />
      <TextField
          variant="outlined"
          multiline
          style={{gridColumn: "1 / span 3"}}
          maxRows={2}
          label='Example'
          value={example}
          required
          onChange={(e) => {
            setExample(e.target.value);
          }}
      />
      <TextField
          variant="outlined"
          multiline
          style={{gridColumn: "1 / span 3"}}
          label='Synonyms'
          value={synonyms}
          onChange={(e) => {
            setSynonyms(e.target.value);
          }}
      />
      <Select
          disabled={inEditMode}
          value={langCode}
          defaultValue={'language'}
          onChange={(e) => {
            setLangCode(e.target.value)
          }}
          displayEmpty
          fullWidth
          style={{gridColumn: '2/ span 1'}}
      >
        <MenuItem value='' disabled>
          <em>Language</em>
        </MenuItem>
        <MenuItem value={'en'}>English</MenuItem>
        <MenuItem value={'ua'}>Українська</MenuItem>
        <MenuItem value={'es'}>Español</MenuItem>
      </Select>
      <Button variant="contained" color="secondary" type="submit">Submit</Button>
    </form>
  </div>;
}

export default Form;
