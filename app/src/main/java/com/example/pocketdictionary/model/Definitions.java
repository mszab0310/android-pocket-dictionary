package com.example.pocketdictionary.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;

@Entity(tableName = "definitions",
        foreignKeys = {@ForeignKey(entity = WordEntry.class,
        parentColumns = "id",
        childColumns = "wordId",
        onDelete = ForeignKey.CASCADE)}
)
public class Definitions implements WordDetailType {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String definition;

    private String partOfSpeech;

    private Long wordId;

    public Definitions(String definition, Long wordId, String partOfSpeech) {
        this.definition = definition;
        this.wordId = wordId;
        this.partOfSpeech = partOfSpeech;
    }

    @Ignore
    public Definitions(String definition, String partOfSpeech) {
        this.definition = definition;
        this.partOfSpeech = partOfSpeech;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @NonNull
    @Override
    public String getString() {
        return StringUtils.capitalize(partOfSpeech) + ": " + StringUtils.capitalize(definition);
    }
}
