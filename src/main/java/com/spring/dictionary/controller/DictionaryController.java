package com.spring.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.spring.dictionary.entity.Translation;
import com.spring.dictionary.entity.Word;
import com.spring.dictionary.service.DictionaryService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/dictionary/api")
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
