import {capitalize} from '../../../utils/funcs';
import Service from '../../../services/service';
import {useState} from 'react';
import './homePage.scss';
import SearchBox from '../../searchBox/searchBox';
import WordCard from '../../wordCard/wordCard';
import Message from '../../message/message';

const homePage = () => {
  const [word, setWord] = useState();
  const [searchRequest, setSearchRequest] = useState('');
  const [translation, setTranslation] = useState();
  // TODO: investigate opportunity to add spinner on loading
  // const [loading, setLoading] = useState('');

  const service = new Service();

  const setWordInfo = async (selectedLanguage) => {
    console.log(searchRequest);
    const res = await service.getWord(selectedLanguage, searchRequest);
    // setLoading(true);
    setWord(res);
    // setLoading(false);
  };

  const setTranslationResult = async (selectedLanguage, translateLanguage) => {
    const payload = {
      word: word.word,
      fromLanguageCode: selectedLanguage,
      toLanguageCode: translateLanguage
    };
    // setLoading(true);
    const res = await service.getTranslation(payload);
    setTranslation(res);
    // setLoading(false);
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
          ) /*: loading ? (
              <Message>Loading</Message>
          )*/ : (
              <Message>Word was not found</Message>
          )}
        </div>
      </div>
  );
};

export default homePage;
