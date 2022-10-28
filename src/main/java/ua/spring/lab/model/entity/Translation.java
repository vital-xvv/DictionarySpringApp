package ua.spring.lab.model.entity;

public class Translation {

    private String word;
    private String fromLanguageCode;
    private String toLanguageCode;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFromLanguageCode() {
        return fromLanguageCode;
    }

    public void setFromLanguageCode(String fromLanguageCode) {
        this.fromLanguageCode = fromLanguageCode;
    }

    public String getToLanguageCode() {
        return toLanguageCode;
    }

    public void setToLanguageCode(String toLanguageCode) {
        this.toLanguageCode = toLanguageCode;
    }
}
