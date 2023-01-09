package com.example.pocketdictionary.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "rhymes",foreignKeys = {@ForeignKey(entity = WordEntry.class,
        parentColumns = "id",
        childColumns = "wordId",
        onDelete = ForeignKey.CASCADE)}
)
public class Rhymes {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String rhyme;

    private Long wordId;

    public Rhymes(String rhyme, Long wordId) {
        this.rhyme = rhyme;
        this.wordId = wordId;
    }

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
}
