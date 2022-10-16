package ua.spring.lab.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.spring.lab.models.models.Dictionary;
import ua.spring.lab.models.models.Language;
import ua.spring.lab.models.models.WordInfo;
import ua.spring.lab.service.WordInfoService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DictionaryDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/dict_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private WordInfoService wordInfoService;

    @Autowired
    public void setWordInfo(WordInfoService wordInfoService) {
        this.wordInfoService = wordInfoService;
    }

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
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

    public WordInfo getWord(String word, Language language){
        WordInfo wordInfo = null;
        try {
            PreparedStatement statement = null;
            switch (language){
                case ENGLISH: {
                    statement = connection.prepareStatement("SELECT * FROM words WHERE en=?");
                    statement.setString(1,word);
                break;}
                case UKRAINIAN:{
                    statement = connection.prepareStatement("SELECT * FROM words WHERE ua=?");
                    statement.setString(1,word);
                break;}
                case SPANISH:{
                    statement = connection.prepareStatement("SELECT * FROM words WHERE es=?");
                    statement.setString(1,word);
                }
            }

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                wordInfo = wordInfoService.fromJson(resultSet.getString(language.countryCode + "_json"));
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to get a searched word + ' " + word + " '");
            throwables.printStackTrace();
        }
        return wordInfo;
    }

    public List<WordInfo> getWords(Language language){
        List<WordInfo> wordInfos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            switch (language){
                case ENGLISH: {
                    resultSet = statement.executeQuery("SELECT en_json FROM words");
                    break;}
                case UKRAINIAN:{
                    resultSet = statement.executeQuery("SELECT ua_json FROM words");
                    break;}
                case SPANISH:{
                    resultSet = statement.executeQuery("SELECT es_json FROM words");
                }
            }
            while(resultSet.next()) {
                System.out.println(resultSet.getString(language.countryCode + "_json"));
                WordInfo wordInfo = wordInfoService.fromJson(resultSet.getString(language.countryCode + "_json"));
                wordInfos.add(wordInfo);
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to get all words from database");
            throwables.printStackTrace();
        }
        return wordInfos;
    }

}
