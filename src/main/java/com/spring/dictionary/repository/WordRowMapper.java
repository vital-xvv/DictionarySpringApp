package com.spring.dictionary.repository;


import com.spring.dictionary.entity.Word;
import com.spring.dictionary.service.WordService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class WordRowMapper implements RowMapper<Word> {

    private String languageCode;

    public WordRowMapper(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
        WordService wordService = new WordService();
        return wordService.getWordFromJson(rs.getString(languageCode + "_json"));

    }
}
