package com.spring.dictionary.entity;

import lombok.Data;

@Data
public class PasswordDTO {
    private String oldRawPassword;
    private String newRawPassword;
}
