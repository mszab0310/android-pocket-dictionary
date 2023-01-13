package com.example.pocketdictionary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "rhymes",
        foreignKeys = {@ForeignKey(entity = WordEntry.class,
        parentColumns = "id",
        childColumns = "wordId",
        onDelete = ForeignKey.CASCADE)}
)
public class Rhymes implements WordDetailType{
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String rhyme;

    private Long wordId;

    public Rhymes(String rhyme, Long wordId) {
        this.rhyme = rhyme;
        this.wordId = wordId;
    }

    public Rhymes(Long id, String rhyme, Long wordId) {
        this.id = id;
        this.rhyme = rhyme;
        this.wordId = wordId;
    }

    @Ignore
    public Rhymes(String rhyme) {
        this.rhyme = rhyme;
    }

    public Rhymes(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRhyme() {
        return rhyme;
    }

    public void setRhyme(String rhyme) {
        this.rhyme = rhyme;
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
        return rhyme;
    }
}
