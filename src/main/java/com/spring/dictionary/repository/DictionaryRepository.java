package com.spring.dictionary.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dictionary.entity.PageOfWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.spring.dictionary.entity.Language;
import com.spring.dictionary.entity.Word;
import com.spring.dictionary.service.WordService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DictionaryRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/dict_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static ObjectMapper mapper = new ObjectMapper();


    private final WordService wordService;

    @Autowired
    public DictionaryRepository(WordService wordService) {
        this.wordService = wordService;
    }


    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to a database");
        } catch (SQLException throwables) {
            System.out.println("Failed to connect to a database");
            throwables.printStackTrace();
        }
    }


    public Word getWord(String word, Language language){
        Word wordInfo = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM words WHERE " + language.countryCode + "=?");
            statement.setString(1,word);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                wordInfo = wordService.getWordFromJson(resultSet.getString(language.countryCode + "_json"));
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to get a searched word + ' " + word + " '");
            throwables.printStackTrace();
        }
        return wordInfo;
    }


    public List<Word> getWords(Language language){
        List<Word> words = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + language.countryCode + "_json FROM words");;
            while(resultSet.next()) {
                Word word = wordService.getWordFromJson(resultSet.getString(language.countryCode + "_json"));
                words.add(word);
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to get all words from database");
            throwables.printStackTrace();
        }
        return words;
    }




    public Word getTranslation(String word, Language fromLanguage, Language toLanguage) {
        Word wordInfo = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM words WHERE " + fromLanguage.countryCode + "=?");
            statement.setString(1,word);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                wordInfo = wordService.getWordFromJson(resultSet.getString(toLanguage.countryCode + "_json"));
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to get a searched word + ' " + word + " '");
            throwables.printStackTrace();
        }
        return wordInfo;
    }

    public Word changeWord(String word, Language language, Word newWord) {
        String json = null;
        try {
            json = mapper.writeValueAsString(newWord);
        } catch (JsonProcessingException e) {
            System.err.println("Failed to convert json");
            throw new RuntimeException(e);
        }
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE words SET " + language.countryCode + "_json=?," + language.countryCode
                    + "=? WHERE " + language.countryCode + "=?");
            statement.setString(1, json);
            statement.setString(2,newWord.getWord());
            statement.setString(3, word);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update word: " + word + " in the database");
            throw new RuntimeException(e);
        }
        return newWord;
    }

    public void addWord(List<String> words, List<Word> correspondingObjs) {

        List<String> jsons = correspondingObjs.stream().map(o -> {
            try {
                return mapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                System.err.println("Unable to create new word in the database - wrong json");
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        try {
            PreparedStatement statement = connection.
                    prepareStatement("INSERT INTO words (en, ua, es, en_json, ua_json, es_json) VALUES (?, ?, ? ,?, ?, ?)");
            statement.setString(1, words.get(0));
            statement.setString(2, words.get(1));
            statement.setString(3, words.get(2));
            statement.setString(4, jsons.get(0));
            statement.setString(5, jsons.get(1));
            statement.setString(6, jsons.get(2));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to insert word into the database");
            throw new RuntimeException(e);
        }

    }

    public void deleteWord(String word, Language language) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM words WHERE " + language.countryCode + "=?");
            statement.setString(1, word);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete word: " + word + " from the database");
            throw new RuntimeException(e);
        }

    }


    public PageOfWords findWordsWithPagination(int offset, int pageSize, Language language) {
        PreparedStatement statement;
        PreparedStatement statement2;
        int numberOfPages;
        int numberOfWords;
        int pageNumber = offset + 1;
        List<Word> words = new ArrayList<>();
        PageOfWords page = new PageOfWords();

        try {
            statement = connection.prepareStatement("SELECT COUNT(*) as number FROM words");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            numberOfWords = resultSet.getInt("number");
            if(numberOfWords % pageSize == 0) {
                numberOfPages = numberOfWords / pageSize;
            } else {
                numberOfPages = numberOfWords / pageSize + 1;
            }
            String SQL = "SELECT " + language.countryCode +"_json FROM words LIMIT ? OFFSET ?";
            statement2 = connection.prepareStatement(SQL);

            statement2.setInt(1, pageSize);
            statement2.setInt(2, pageSize * offset);

            ResultSet resultSet1 = statement2.executeQuery();

            while(resultSet1.next()) {
                Word newWord = wordService.getWordFromJson(resultSet1.getString(language.countryCode+"_json"));
                words.add(newWord);
            }

            page.setWords(words);
            page.setPageNumber(pageNumber);
            page.setNumberOfPages(numberOfPages);
            page.setNumberOfWords(numberOfWords);

            return page;


        } catch (SQLException e) {
            System.err.println("Database error returning PageOfWords " + e.getMessage());
        }
        return null;
    }

    public List<Word> findWordsFilterByPartOfSpeech(String partOfSpeech, Language language, boolean reverse) {
        List<Word> words = new ArrayList<>();
        String SQL = "SELECT " + language.countryCode + "_json FROM words";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Word newWord = wordService.getWordFromJson(resultSet.getString(language.countryCode+"_json"));
                if(!reverse) {
                    if(newWord.getPartOfSpeech().trim().equals(partOfSpeech)) words.add(newWord);

                }
                else {
                    if(!newWord.getPartOfSpeech().trim().equals(partOfSpeech)) words.add(newWord);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return words;
    }


    public List<Word> findWordsFilterByStartsWith(String startsWith, Language language, boolean reverse) {
        List<Word> words = new ArrayList<>();
        String SQL = "SELECT " + language.countryCode + "_json FROM words";
        try {
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Word newWord = wordService.getWordFromJson(resultSet.getString(language.countryCode+"_json"));
                if(!reverse) {
                    if(newWord.getWord().trim().startsWith(startsWith)) words.add(newWord);

                }
                else {
                    if(!newWord.getWord().trim().startsWith(startsWith)) words.add(newWord);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return words;
    }
}
