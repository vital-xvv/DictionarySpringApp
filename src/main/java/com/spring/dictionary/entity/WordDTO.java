package com.spring.dictionary.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordDTO {

    private String word;
    private String langCode;
    private Word wordObject;

}
