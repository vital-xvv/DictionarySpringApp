package ua.spring.lab.model.entity;

public enum Language {
    ENGLISH("en"),
    UKRAINIAN("ua"),
    SPANISH("es");

    public final String countryCode;

    Language (String countryCode){
        this.countryCode = countryCode;
    }
}
