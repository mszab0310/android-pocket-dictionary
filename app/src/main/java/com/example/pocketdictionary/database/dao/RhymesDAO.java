package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Rhymes;
import com.example.pocketdictionary.model.WordEntry;

import java.util.List;

@Dao
public interface RhymesDAO {
    @Query("SELECT * FROM rhymes")
    List<WordEntry> getAll();

    @Query("SELECT * FROM rhymes WHERE rhyme LIKE :query")
    WordEntry findByName(String query);

    @Query("SELECT * from rhymes where id = :id")
    WordEntry findById(Long id);

    @Insert
    void insert(Rhymes rhymes);

    @Delete
    void delete(Rhymes rhymes);
}
