package com.example.pocketdictionary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "synonyms",
        foreignKeys = {@ForeignKey(entity = WordEntry.class,
        parentColumns = "id",
        childColumns = "wordId",
        onDelete = ForeignKey.CASCADE)})
public class Synonyms implements WordDetailType{
    @PrimaryKey(autoGenerate = true)
    public Long id;

    private String synonym;

    public Long wordId;


    public Synonyms(String synonym, Long wordId) {
        this.synonym = synonym;
        this.wordId = wordId;
    }
    @Ignore
    public Synonyms(String synonym ) {
        this.synonym = synonym;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
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
        return synonym;
    }
}
