package com.spring.dictionary.entity;

import lombok.Data;

@Data
public class FilterDTO {
    private boolean reverse;
    private String langCode;
    private String partOfSpeech;
    private String startsWith;
}
