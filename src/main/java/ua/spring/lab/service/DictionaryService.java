package ua.spring.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.spring.lab.DAO.DictionaryDAO;
import ua.spring.lab.model.entity.Language;
import ua.spring.lab.model.entity.Translation;
import ua.spring.lab.model.entity.Word;

import java.util.List;


@Service
public class DictionaryService {

    private DictionaryDAO dictionaryDAO;
    @Autowired
    public void setDictionaryDAO(DictionaryDAO dictionaryDAO){
        this.dictionaryDAO = dictionaryDAO;
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
        return dictionaryDAO.getWord(word,language);
    }



    public List<Word> getWords(String lang) {
        Language language = convertLanguage(lang);
        return dictionaryDAO.getWords(language);
    }



    public Word getTranslation(Translation translation) {
        Language language = convertLanguage(translation.getFromLanguageCode());
        Language toLanguage = convertLanguage(translation.getToLanguageCode());
        return dictionaryDAO.getTranslation(translation.getWord(), language, toLanguage);
    }
}
