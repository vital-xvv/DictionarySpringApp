package com.spring.dictionary.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageOfWords {
    private int numberOfPages;
    private int pageNumber;
    private int numberOfWords;
    private List<Word> words;
}
