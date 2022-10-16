package ua.spring.lab.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import ua.spring.lab.models.models.WordInfo;




@Service
public class WordInfoService {
    private static final Gson GSON = new GsonBuilder().create();

    public WordInfo fromJson(String json){
        return GSON.fromJson(json, WordInfo.class);
    }
}
