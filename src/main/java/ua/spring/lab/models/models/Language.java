package ua.spring.lab.models.models;

public enum Language {
    ENGLISH("en"),
    UKRAINIAN("ua"),
    SPANISH("es");

    public final String countryCode;

    Language (String countryCode){
        this.countryCode = countryCode;
    }
}
