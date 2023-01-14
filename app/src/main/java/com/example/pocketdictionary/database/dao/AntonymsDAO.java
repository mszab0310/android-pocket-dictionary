package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.Definitions;

import java.util.List;

@Dao
public interface AntonymsDAO {
    @Query("SELECT * FROM antonyms")
    List<Antonyms> getAll();

    @Query("SELECT * FROM antonyms WHERE antonym  LIKE :query")
    Antonyms findByName(String query);

    @Query("SELECT * from antonyms where id = :id")
    Antonyms findById(Long id);

    @Query("SELECT * FROM antonyms WHERE wordId = :wordId")
    List<Antonyms> getAntonymsForWord(long wordId);

    @Insert
    void insert(Antonyms antonyms);

    @Delete
    void delete(Antonyms antonyms);
}
