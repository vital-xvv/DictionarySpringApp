package ua.spring.lab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.spring.lab.DAO.DictionaryDAO;
import ua.spring.lab.models.models.Language;

@Controller
public class DictionaryController {


    private DictionaryDAO dictionaryDAO;

    @Autowired
    public void setDictionaryDAO(DictionaryDAO dictionaryDAO){
        this.dictionaryDAO = dictionaryDAO;
    }

    @GetMapping("/{lang}/{word}")
    public String hello(@PathVariable("word") String word, @PathVariable("lang") String lang, Model model){
        Language language = Language.ENGLISH;
        switch (lang) {
            case "ua": {
                language = Language.UKRAINIAN;
                break;}
            case "es": {
                language = Language.SPANISH;
                break;
            }
        }
        model.addAttribute("wordInfo", dictionaryDAO.getWord(word, language));
        return "word";
    }

    @GetMapping("/{lang}")
    public String index(@PathVariable("lang") String lang, Model model){
        Language language = Language.ENGLISH;
        switch (lang) {
            case "ua": {
                language = Language.UKRAINIAN;
                break;}
            case "es": {
                language = Language.SPANISH;
                break;
            }
        }
        model.addAttribute("wordInfos", dictionaryDAO.getWords(language));
        return "words";
    }


}
