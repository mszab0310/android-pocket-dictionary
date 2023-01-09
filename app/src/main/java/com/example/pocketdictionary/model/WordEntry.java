package com.example.pocketdictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "words")
public class WordEntry {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String word;
    private List<String> definitions;
    private List<String> synonyms;
    private List<String> antonyms;
    private List<String> rhymes;

    public WordEntry(String word) {
        this.word = word;
        definitions = new ArrayList<>();
        synonyms = new ArrayList<>();
        antonyms = new ArrayList<>();
        rhymes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public List<String> getRhymes() {
        return rhymes;
    }

    public void setRhymes(List<String> rhymes) {
        this.rhymes = rhymes;
    }
}
