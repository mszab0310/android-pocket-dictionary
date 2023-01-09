package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Definitions;
import com.example.pocketdictionary.model.WordEntry;

import java.util.List;

@Dao
public interface DefinitionsDAO {
    @Query("SELECT * FROM definitions")
    List<WordEntry> getAll();

    @Query("SELECT * FROM definitions WHERE definition LIKE :query")
    WordEntry findByName(String query);

    @Query("SELECT * from definitions where id = :id")
    WordEntry findById(Long id);

    @Insert
    void insert(Definitions definitions);

    @Delete
    void delete(Definitions definitions);
}