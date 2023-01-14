package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Definitions;

import java.util.List;

@Dao
public interface DefinitionsDAO {
    @Query("SELECT * FROM definitions")
    List<Definitions> getAll();

    @Query("SELECT * FROM definitions WHERE definition LIKE :query")
    Definitions findByName(String query);

    @Query("SELECT * from definitions where id = :id")
    Definitions findById(Long id);

    @Query("SELECT * FROM definitions WHERE wordId = :wordId")
    List<Definitions> getDefinitionsForWord(long wordId);

    @Insert
    void insert(Definitions definitions);

    @Delete
    void delete(Definitions definitions);
}
