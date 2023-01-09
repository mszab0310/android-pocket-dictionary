package com.example.pocketdictionary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class WordEntry {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    private String word;

    public WordEntry(String word) {
        this.word = word;
    }
    public WordEntry(){}

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

}
