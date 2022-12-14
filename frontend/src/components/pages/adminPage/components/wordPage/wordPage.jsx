import {useLocation, useParams} from "react-router-dom";

import WordCard from "../../../../wordCard/wordCard";
import useService from "../../../../../services/useService";
import {useEffect, useState} from "react";

import './wordPage.scss'

const wordPage = () => {
  const {word: paramWord} = useParams();
  const {state} = useLocation()
  const {getWord} = useService();
  const [word, setWord] = useState({});
  useEffect(() => {
    (async () => {
      console.log(state)
      const {data} = await getWord(state.lang, paramWord)
      setWord(data);
    })()
  }, []);

  return (
      <div className="word-page">
        <div className="admin-container">
          <WordCard word={word}></WordCard>
        </div>
      </div>
  );
}

export default wordPage;
