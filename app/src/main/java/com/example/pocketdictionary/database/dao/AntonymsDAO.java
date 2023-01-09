package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.WordEntry;

import java.util.List;

@Dao
public interface AntonymsDAO {
    @Query("SELECT * FROM antonyms")
    List<WordEntry> getAll();

    @Query("SELECT * FROM antonyms WHERE antonym  LIKE :query")
    WordEntry findByName(String query);

    @Query("SELECT * from antonyms where id = :id")
    WordEntry findById(Long id);

    @Insert
    void insert(Antonyms antonyms);

    @Delete
    void delete(Antonyms antonyms);
}
