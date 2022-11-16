package com.spring.dictionary.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.spring.dictionary.entity.Language;
import com.spring.dictionary.entity.Word;
import com.spring.dictionary.service.WordService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DictionaryRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/dict_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";


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


}
