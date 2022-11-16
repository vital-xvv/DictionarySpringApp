package com.spring.dictionary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.spring.dictionary.entity.Word;

@Service
public class WordService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Word getWordFromJson(String json){
        try {
            return objectMapper.readValue(json, Word.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
