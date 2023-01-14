package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Rhymes;
import com.example.pocketdictionary.model.Synonyms;

import java.util.List;

@Dao
public interface SynonymsDAO {
    @Query("SELECT * FROM synonyms")
    List<Synonyms> getAll();

    @Query("SELECT * FROM synonyms WHERE synonym LIKE :query")
    Synonyms findByName(String query);

    @Query("SELECT * from synonyms where id = :id")
    Synonyms findById(Long id);

    @Query("SELECT * FROM synonyms WHERE wordId = :wordId")
    List<Synonyms> getSynonymsForWord(long wordId);

    @Insert
    void insert(Synonyms synonym);

    @Delete
    void delete(Synonyms synonyms);
}
