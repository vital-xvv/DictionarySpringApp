package ua.spring.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.spring.lab.model.entity.Translation;
import ua.spring.lab.model.entity.Word;
import ua.spring.lab.service.DictionaryService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }


    @GetMapping("/{lang}/{word}")
    public Word getWord(@PathVariable("word") String word, @PathVariable("lang") String lang){
       return dictionaryService.getWord(word, lang);
    }


    @GetMapping("/{lang}")
    public List<Word> getWords(@PathVariable("lang") String lang){
       return dictionaryService.getWords(lang);
    }


    @PostMapping("/translate")
    public Word translate(@RequestBody Translation translation){
        return dictionaryService.getTranslation(translation);
    }



}
