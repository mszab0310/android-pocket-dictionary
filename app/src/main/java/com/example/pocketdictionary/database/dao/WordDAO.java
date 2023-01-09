package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.WordEntry;

import java.util.List;

@Dao
public interface WordDAO {
    @Query("SELECT * FROM words")
    List<WordEntry> getAll();

    @Query("SELECT * FROM words WHERE word LIKE :query")
    WordEntry findByName(String query);

    @Query("SELECT * from words where id = :id")
    WordEntry findById(Long id);

    @Insert
    void insert(WordEntry wordEntry);

    @Delete
    void delete(WordEntry wordEntry);
}
