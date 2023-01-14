package com.example.pocketdictionary.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.Rhymes;

import java.util.List;


@Dao
public interface RhymesDAO {
    @Query("SELECT * FROM rhymes")
    List<Rhymes> getAll();

    @Query("SELECT * FROM rhymes WHERE rhyme LIKE :query")
    Rhymes findByName(String query);

    @Query("SELECT * from rhymes where id = :id")
    Rhymes findById(Long id);

    @Query("SELECT * FROM rhymes WHERE wordId = :wordId")
    List<Rhymes> getRhymesForWord(long wordId);

    @Insert
    void insert(Rhymes rhymes);

    @Delete
    void delete(Rhymes rhymes);
}
