package com.spring.dictionary.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dictionary.entity.PageOfWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.spring.dictionary.entity.Language;
import com.spring.dictionary.entity.Word;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {

    private final JdbcTemplate jdbcTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public DictionaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public Word getWord(String word, Language language){
        return jdbcTemplate.query("SELECT * FROM words WHERE " + language.countryCode + "=?", new Object[]{word}, new WordRowMapper(language.countryCode))
                .stream().findAny().orElse(null) ;
    }


    public List<Word> getWords(Language language){
        return jdbcTemplate.query("SELECT " + language.countryCode + "_json FROM words", new Object[]{}, new WordRowMapper(language.countryCode));
    }


    public Word getTranslation(String word, Language fromLanguage, Language toLanguage) {
        return jdbcTemplate.query("SELECT * FROM words WHERE " + fromLanguage.countryCode + "=?", new Object[]{word}, new WordRowMapper(toLanguage.countryCode))
                .stream().findAny().orElse(null);
    }

    public Word changeWord(String word, Language language, Word newWord) throws JsonProcessingException {
        String json = mapper.writeValueAsString(newWord);

        jdbcTemplate.update("UPDATE words SET " + language.countryCode + "_json=?," + language.countryCode +
                "=? WHERE " + language.countryCode + "=?", json, newWord.getWord(), word);
        return newWord;
    }


    public void addWord(List<String> words, List<Word> correspondingObjs) {

        List<String> jsons = correspondingObjs.stream().map(o -> {
            try {
                return mapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        jdbcTemplate.update("INSERT INTO words (en, ua, es, en_json, ua_json, es_json) VALUES (?, ?, ? ,?, ?, ?)", words.get(0), words.get(1),words.get(2),
                jsons.get(0), jsons.get(1), jsons.get(2));

    }

    public void deleteWord(String word, Language language) {
        jdbcTemplate.update("DELETE FROM words WHERE " + language.countryCode + "=?", word);
    }


    @Transactional
    public PageOfWords findWordsWithPagination(int offset, int pageSize, Language language) {
        int numberOfPages;
        int numberOfWords;
        int pageNumber = offset + 1;
        List<Word> words;
        PageOfWords page = new PageOfWords();

        numberOfWords = jdbcTemplate.query("SELECT en_json FROM words", new WordRowMapper("en")).size();

        if(numberOfWords % pageSize == 0) {
            numberOfPages = numberOfWords / pageSize;
        } else {
            numberOfPages = numberOfWords / pageSize + 1;
        }
        words = jdbcTemplate.query("SELECT " + language.countryCode +"_json FROM words LIMIT ? OFFSET ?",
                new Object[]{pageSize, pageSize * offset}, new WordRowMapper(language.countryCode));

        page.setWords(words);
        page.setPageNumber(pageNumber);
        page.setNumberOfPages(numberOfPages);
        page.setNumberOfWords(numberOfWords);

        return page;
    }

    public List<Word> findWordsFilterByPartOfSpeech(String partOfSpeech, Language language, boolean reverse) {
        return jdbcTemplate.query("SELECT " + language.countryCode + "_json FROM words", new WordRowMapper(language.countryCode))
                .stream().map(word -> {
                    Word selectedWord = null;
                    if(!reverse) {
                        if(word.getPartOfSpeech().trim().equals(partOfSpeech)) {
                            selectedWord = word;
                        }
                    }
                    else {
                        if(!word.getPartOfSpeech().trim().equals(partOfSpeech)){
                            selectedWord = word;
                        }
                    }
                    return selectedWord;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    public List<Word> findWordsFilterByStartsWith(String startsWith, Language language, boolean reverse) {

        if(!reverse) {
             return jdbcTemplate.query("SELECT " + language.countryCode + "_json FROM words WHERE " + language.countryCode + " LIKE ?",new Object[]{startsWith+"%"},
                     new WordRowMapper(language.countryCode));
         }
         else {
             return jdbcTemplate.query("SELECT " + language.countryCode + "_json FROM words WHERE " + language.countryCode + " NOT LIKE ?", new Object[]{startsWith+"%"},
                     new WordRowMapper(language.countryCode));
         }
    }
}
