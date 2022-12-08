package com.spring.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.dictionary.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.spring.dictionary.service.DictionaryService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }


    @Operation(
            tags={"Dictionary operations"},
            operationId = "get-word-from-dictionary",
            summary = "POST request to retrieve a single word",
            description = "Uses WordDTO object as a request body to specify which word exactly you need.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
                    "You have to provide exact word and language code, wordObject is not necessary here, leave it null",
                    content = @Content(schema = @Schema(implementation = WordDTO.class), examples = {@ExampleObject(name = "Example", value = "{\n" +
                            "    \"word\":\"apple\",\n" +
                            "    \"langCode\":\"en\",\n" +
                            "    \"wordObject\": null\n" +
                            "}")})),
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Word.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Examle", value = "{\n" +
                            "        \"word\": \"apple\",\n" +
                            "        \"transcription\": \"[ˈæp.l̩]\",\n" +
                            "        \"example\": \"Apples are a real rarity in eastern and southern countries\",\n" +
                            "        \"audio\": \"\",\n" +
                            "        \"synonyms\": \"\",\n" +
                            "        \"meaning\": \"juicy fruit which grows up on an apple-tree\",\n" +
                            "        \"partOfSpeech\": \"noun\"\n" +
                            "    }")}))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/get/word")
    public Word getWord(@RequestBody WordDTO wordDTO){
       return dictionaryService.getWord(wordDTO.getWord(), wordDTO.getLangCode());
    }


    @Operation(
            tags={"Dictionary operations"},
            summary = "GET request to retrieve all words of the language specified",
            description = "Request demands only one path variable containing needed language code",
            parameters = {@Parameter(name = "lang", description = "It contains lowercase language code")},
            responses = {@ApiResponse(responseCode = "200")},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/{lang}")
    public List<Word> getWords(@PathVariable("lang") String lang){
       return dictionaryService.getWords(lang);
    }



    @Operation(
            tags={"Dictionary operations"},
            summary = "POST request to retrieve a translation of the word",
            description = "Uses Translation DTO object as a request body to get a translation of the word to the language you choose",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
                    "You have to provide exact word and language code of the word and language code of the translation.",
                    content = @Content(schema = @Schema(implementation = Translation.class), examples = {@ExampleObject(name = "Example", value = "{\n" +
                            "    \"word\":\"apple\",\n" +
                            "    \"fromLanguageCode\":\"en\",\n" +
                            "    \"toLanguageCode\":\"es\"\n" +
                            "}")})),
            security = {@SecurityRequirement(name = "BearerJWT")}

    )
    @PostMapping("/translate")
    public Word translate(@RequestBody Translation translation){
        return dictionaryService.getTranslation(translation);
    }


    @Operation(
            tags={"Dictionary operations"},
            summary = "DELETE request to delete all information of the word from dictionary, returns void.",
            description = "Uses WordDTO object as a request body to get a info of a word that will be deleted, wordObject can be null.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
                    "You have to provide exact word and language code, wordObject is not necessary here, leave it null",
                    content = @Content(schema = @Schema(implementation = WordDTO.class), examples = {@ExampleObject(name = "Example", value = "{\n" +
                            "    \"word\":\"apple\",\n" +
                            "    \"langCode\":\"en\",\n" +
                            "    \"wordObject\": null\n" +
                            "}")})),
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @DeleteMapping("/delete/word")
    public void deleteWord(@RequestBody WordDTO wordDTO) {
        dictionaryService.deleteWord(wordDTO.getWord(), wordDTO.getLangCode());
    }


    @Operation(
            tags={"Dictionary operations"},
            summary = "PUT request to change info of the word specified.",
            description = "Uses WordDTO object as a request body to change the word, wordObject is required and will be used as new info about the word.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description =
                    "You have to provide exact word and language code, wordObject is required too.",
                    content = @Content(schema = @Schema(implementation = WordDTO.class), examples = {@ExampleObject(name = "Example", value = "{\n" +
                            "    \"word\":\"apple\",\n" +
                            "    \"langCode\":\"en\",\n" +
                            "    \"wordObject\":  {\n" +
                            "    \"word\": \"apple\",\n" +
                            "    \"transcription\": \"[ˈæp.l̩]\",\n" +
                            "    \"example\": \"Apples are so tasty fruits\",\n" +
                            "    \"audio\": \"\",\n" +
                            "    \"synonyms\": \"something was here before\",\n" +
                            "    \"meaning\": \"juicy fruit which grows up on an apple-tree\",\n" +
                            "    \"partOfSpeech\": \"noun\"\n" +
                            "}" +
                            "}")})),
            responses = {@ApiResponse(responseCode = "202", content = @Content(schema = @Schema(implementation = Word.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Examle", value = "{\n" +
                            "    \"word\": \"apple\",\n" +
                            "    \"transcription\": \"[ˈæp.l̩]\",\n" +
                            "    \"example\": \"Apples are so tasty fruits\",\n" +
                            "    \"audio\": \"\",\n" +
                            "    \"synonyms\": \"something was here before\",\n" +
                            "    \"meaning\": \"juicy fruit which grows up on an apple-tree\",\n" +
                            "    \"partOfSpeech\": \"noun\"\n" +
                            "}")}))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PutMapping("/change/word")
    public Word changeWord(@RequestBody WordDTO wordDTO) throws JsonProcessingException {
        return dictionaryService.changeWord(wordDTO.getWord(), wordDTO.getLangCode(), wordDTO.getWordObject());
    }


    @Operation(
            tags={"Dictionary operations"},
            summary = "POST request to create a new word in the Dictionary.",
            description = "Uses DictRowDTO object as a request body to create a new word in the dictionary.",
            responses = {@ApiResponse(responseCode = "202")},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/add/word")
    public void addWord(@RequestBody DictRowDTO dictRowDTO) {
        dictionaryService.addWord(dictRowDTO.getWords(), dictRowDTO.getObjects());
    }

    @Operation (
            tags={"DictionaryAPI Pagination and Filtering"},
            summary = "GET request to retrieve pages of words with exact size and page number.",
            description = "Request demands only one path variable containing needed language code",
            parameters = {@Parameter(name = "lang", description = "It contains lowercase language code"),
                          @Parameter(name="offset", description = "Contains a page number but decremented."),
                          @Parameter(name = "pageSize", description = "Keeps a value of number of needed elements.")},
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PageOfWords.class)))},
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/words/{offset}/{pageSize}/{lang}")
    public PageOfWords findWordsWithPagination(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String lang){
        return dictionaryService.findWordsWithPagination(offset, pageSize, lang);
    }


    @Operation (
            tags={"DictionaryAPI Pagination and Filtering"},
            summary = "POST request to retrieve filtered list of words by partOfSpeech property.",
            description = "Request demands request body of FilterDTO class. All properties must have some value including null.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = FilterDTO.class))),
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/words/filter/by/partOfSpeech")
    public List<Word> findWordsFilterByPartOfSpeech(@RequestBody FilterDTO filterDTO) {
        return dictionaryService.findWordsFilterByPartOfSpeech(filterDTO.getPartOfSpeech(), filterDTO.getLangCode(), filterDTO.isReverse());
    }

    @Operation (
            tags={"DictionaryAPI Pagination and Filtering"},
            summary = "POST request to retrieve filtered list of words by starting letters.",
            description = "Request demands request body of FilterDTO class. All properties must have some value including null.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = FilterDTO.class))),
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/words/filter/by/startsWith")
    public List<Word> findWordsThatStartsWith(@RequestBody FilterDTO filterDTO) {
        return dictionaryService.findWordsFilterByStartsWith(filterDTO.getStartsWith(), filterDTO.getLangCode(), filterDTO.isReverse());
    }
}
