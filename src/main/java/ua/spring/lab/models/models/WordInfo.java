package ua.spring.lab.models.models;

public class WordInfo {
    private String word;
    private String transcription;
    private String example;
    private String audio;
    private String synonyms;
    private String meaning;
    private String partOfSpeech;

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getExample() {
        return example;
    }

    public String getAudio() {
        return audio;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    @Override
    public String toString() {
        return "WordInfo{" +
                "word='" + word + '\'' +
                ", transcription='" + transcription + '\'' +
                ", example='" + example + '\'' +
                ", audio='" + audio + '\'' +
                ", synonyms='" + synonyms + '\'' +
                ", meaning='" + meaning + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                '}';
    }
}
