package com.example.pocketdictionary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "antonyms",
        foreignKeys = {@ForeignKey(entity = WordEntry.class,
        parentColumns = "id",
        childColumns = "wordId",
        onDelete = ForeignKey.CASCADE)}
)
public class Antonyms implements WordDetailType {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    private String antonym;

    public Long wordId;

    public Antonyms(String antonym, Long wordId) {
        this.antonym = antonym;
        this.wordId = wordId;
    }

    @Ignore
    public Antonyms(String antonym) {
        this.antonym = antonym;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    @NonNull
    @Override
    public String getString() {
        return antonym;
    }
}
