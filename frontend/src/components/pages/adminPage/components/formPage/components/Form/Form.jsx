import useService from "../../../../../../../services/useService";
import {useEffect, useState} from "react";
import {Button, TextField} from "@mui/material";

const Form = (props) => {
  const {
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
    lang
  } = props;

  const {addWord, changeWord} = useService();
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  useEffect(() => {
    if (inEditMode) {
      (async function () {
        const res = await handleFetchWord()
        setWord((prev) => ({...prev, [lang]: res.word}));
        setTranscription((prev) => ({...prev, [lang]: res.transcription}));
        setPartOfSpeech((prev) => ({...prev, [lang]: res.partOfSpeech}));
        setMeaning((prev) => ({...prev, [lang]: res.meaning}));
        setExample((prev) => ({...prev, [lang]: res.example}));
        setSynonyms((prev) => ({...prev, [lang]: res.synonyms}));
      })();
    }
  }, []);

  useEffect(() => {
    checkPayload()
  }, [props]);


  //genius cheeeck
  const checkPayload = () => {
    if ([word, partOfSpeech, meaning, example].map(el => Object.values(el).some(el => !el.length)).every(el => el === false)) {
      return setIsSubmitDisabled(false)
    }
    setIsSubmitDisabled(true)
  }


  const handleSubmit = (e) => {
    e.preventDefault()
    const payload = {
      words: Object.values(word),
      objects:
          Object.keys(word).map(lang => ({
            word: word[lang],
            transcription: transcription[lang],
            example: example[lang],
            audio: null,
            synonyms: synonyms[lang],
            meaning: meaning[lang],
            partOfSpeech: partOfSpeech[lang],
          }))
    }
    console.log(payload)
    inEditMode ? changeWord(payload) : addWord(payload)
  }


  return (<form onSubmit={handleSubmit} className="form">
    <TextField
        variant="outlined"
        required
        value={word[lang]}
        label='Word'
        onChange={(e) => {
          setWord((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <TextField
        variant="outlined"
        label='Transcription'
        value={transcription[lang]}
        onChange={(e) => {
          setTranscription((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <TextField
        variant="outlined"
        label='Part of Speech'
        value={partOfSpeech[lang]}
        required
        onChange={(e) => {
          setPartOfSpeech((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <TextField
        variant="outlined"
        multiline
        style={{gridColumn: "1 / span 3"}}
        maxRows={2}
        label='Meaning'
        value={meaning[lang]}
        required
        onChange={(e) => {
          setMeaning((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <TextField
        variant="outlined"
        multiline
        style={{gridColumn: "1 / span 3"}}
        maxRows={2}
        label='Example'
        value={example[lang]}
        required
        onChange={(e) => {
          setExample((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <TextField
        variant="outlined"
        multiline
        style={{gridColumn: "1 / span 3"}}
        label='Synonyms'
        value={synonyms[lang]}
        onChange={(e) => {
          setSynonyms((prev) => ({...prev, [lang]: e.target.value}));
        }}
    />
    <Button disabled={isSubmitDisabled} variant="contained" color="secondary" type="submit"
            style={{gridColumn: '2/span 1'}}>Submit</Button>
  </form>)
}

export default Form;
