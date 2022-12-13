import {useLocation, useParams} from "react-router-dom";
import {Tab} from "@mui/material";
import {TabContext, TabList, TabPanel} from '@mui/lab';
import {useState} from "react";
import useService from "../../../../../services/useService";
import Form from './components/Form/Form'
import './formPage.scss'
import {Box} from "@mui/system";

const FormPage = () => {
  const params = useParams()
  const inEditMode = !!params.word;
  const {state} = useLocation()
  const [selectedLang, setSelectedLang] = useState(inEditMode ? state.lang : 'en');
  const {getWord} = useService()
  const [word, setWord] = useState({en: '', ua: '', es: ''});
  const [transcription, setTranscription] = useState({en: '', ua: '', es: ''});
  const [partOfSpeech, setPartOfSpeech] = useState({en: '', ua: '', es: ''});
  const [meaning, setMeaning] = useState({en: '', ua: '', es: ''});
  const [example, setExample] = useState({en: '', ua: '', es: ''});
  const [synonyms, setSynonyms] = useState({en: '', ua: '', es: ''});

  const handleFetchWord = async () => {
    return await getWord(state.lang, params.word);
  }

  const props = {
    word,
    transcription,
    partOfSpeech,
    meaning,
    example,
    synonyms,
    setWord,
    setTranscription,
    setPartOfSpeech,
    setMeaning,
    setExample,
    setSynonyms,
    handleFetchWord,
    inEditMode,
    lang: selectedLang,
    params
  }

  const handleChange = (e, newValue) => {
    setSelectedLang(newValue);
  }


  return <div className="form-page">
    <div className="main-heading">{inEditMode ? 'Edit Word' : 'Add Word'}</div>
    <div className="admin-container">
      <Box sx={{width: '100%', typography: 'body1'}}>
        <TabContext value={selectedLang}>
          <TabList onChange={handleChange} aria-label="lab API tabs example" centered>
            <Tab disabled={inEditMode && state.lang !== 'en'} label="English" value="en"/>
            <Tab disabled={inEditMode && state.lang !== 'ua'} label="Ukrainian" value="ua"/>
            <Tab disabled={inEditMode && state.lang !== 'es'} label="Espanol" value="es"/>
          </TabList>

          <TabPanel value="en"><Form {...props}/></TabPanel>
          <TabPanel value="ua"><Form {...props}/></TabPanel>
          <TabPanel value="es"><Form {...props}/></TabPanel>
        </TabContext>
      </Box>
    </div>

  </div>;
}


export default FormPage;
