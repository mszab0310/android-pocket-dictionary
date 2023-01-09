package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Synonyms;
import com.example.pocketdictionary.model.WordEntry;

import java.util.List;

@Dao
public interface SynonymsDAO {
    @Query("SELECT * FROM synonyms")
    List<WordEntry> getAll();

    @Query("SELECT * FROM synonyms WHERE synonym LIKE :query")
    WordEntry findByName(String query);

    @Query("SELECT * from synonyms where id = :id")
    WordEntry findById(Long id);

    @Insert
    void insert(Synonyms synonym);

    @Delete
    void delete(Synonyms synonyms);
}
