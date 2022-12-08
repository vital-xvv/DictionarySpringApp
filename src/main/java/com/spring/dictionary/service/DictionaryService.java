package com.spring.dictionary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.dictionary.entity.PageOfWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.dictionary.repository.DictionaryRepository;
import com.spring.dictionary.entity.Language;
import com.spring.dictionary.entity.Translation;
import com.spring.dictionary.entity.Word;

import java.util.List;


@Service
public class DictionaryService {

    private DictionaryRepository dictionaryRepository;
    @Autowired
    public void setDictionaryDAO(DictionaryRepository dictionaryRepository){
        this.dictionaryRepository = dictionaryRepository;
    }



    public Language convertLanguage(String languageCode) {
        switch (languageCode) {
            case "ua": return Language.UKRAINIAN;
            case "en": return Language.ENGLISH;
            case "es": return Language.SPANISH;
            default: return Language.ENGLISH;
        }
    }



    public Word getWord(String word, String lang) {
        Language language = convertLanguage(lang);
        return dictionaryRepository.getWord(word,language);
    }



    public List<Word> getWords(String lang) {
        Language language = convertLanguage(lang);
        return dictionaryRepository.getWords(language);
    }



    public Word getTranslation(Translation translation) {
        Language language = convertLanguage(translation.getFromLanguageCode());
        Language toLanguage = convertLanguage(translation.getToLanguageCode());
        return dictionaryRepository.getTranslation(translation.getWord(), language, toLanguage);
    }



    public void deleteWord(String word, String lang) {
        Language language = convertLanguage(lang);
        dictionaryRepository.deleteWord(word, language);
    }


    public Word changeWord(String word, String lang, Word newWord) throws JsonProcessingException {
        Language language = convertLanguage(lang);
        return dictionaryRepository.changeWord(word, language, newWord);
    }



    public void addWord(List<String> words, List<Word> objects) {
        dictionaryRepository.addWord(words, objects);

    }

    public PageOfWords findWordsWithPagination(int offset, int pageSize, String lang) {
        Language language = convertLanguage(lang);
        return dictionaryRepository.findWordsWithPagination(offset, pageSize, language);
    }

    public List<Word> findWordsFilterByPartOfSpeech(String partOfSpeech, String lang, boolean reverse) {
        Language language = convertLanguage(lang);
        return dictionaryRepository.findWordsFilterByPartOfSpeech(partOfSpeech, language, reverse);
    }

    public List<Word> findWordsFilterByStartsWith(String startsWith, String lang, boolean reverse) {
        Language language = convertLanguage(lang);
        return dictionaryRepository.findWordsFilterByStartsWith(startsWith, language, reverse);
    }
}
