import {capitalize} from '../../../utils/funcs';
import useService from '../../../services/useService';
import {useEffect, useState} from 'react';
import './homePage.scss';
import SearchBox from '../../searchBox/searchBox';
import WordCard from '../../wordCard/wordCard';
import Message from '../../message/message';

const homePage = () => {
  const [word, setWord] = useState();
  const [searchRequest, setSearchRequest] = useState('');
  const [translation, setTranslation] = useState();
  const {getWord, process, getTranslation, getCurrentUser} = useService();

  useEffect( () => {
    getCurrentUser()
  }, []);



  const setWordInfo = async (selectedLanguage) => {
    const res = await getWord(selectedLanguage, searchRequest);
    setWord(res);
  };

  const setTranslationResult = async (selectedLanguage, translateLanguage) => {
    const payload = {
      word: word.word,
      fromLanguageCode: selectedLanguage,
      toLanguageCode: translateLanguage
    };
    const res = await getTranslation(payload);
    setTranslation(res);
  };

  return (
      <div className="home-page">
        <h1 className="main-heading">{searchRequest ? capitalize(searchRequest) : 'WordEra'}</h1>
        <div className='main-container'>
          <SearchBox
              setWordInfo={setWordInfo}
              searchRequest={searchRequest}
              setSearchRequest={setSearchRequest}
              setTranslationResult={setTranslationResult}
          />

          {!searchRequest ? (
              <Message>Enter your word</Message>
          ) : word ? (
              <WordCard word={word} translation={translation}/>
          ) : process === 'loading' ? (
              <Message>Loading</Message>
          ) : process === 'waiting' && !word ? (
              <Message>Word was not found</Message>
          ) : process === 'error' ? (
              <Message>Error, something wrong</Message>
          ): null}
        </div>
      </div>
  );
};

export default homePage;
