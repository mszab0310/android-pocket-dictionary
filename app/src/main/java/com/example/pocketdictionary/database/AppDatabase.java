package com.example.pocketdictionary.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pocketdictionary.database.dao.AntonymsDAO;
import com.example.pocketdictionary.database.dao.DefinitionsDAO;
import com.example.pocketdictionary.database.dao.RhymesDAO;
import com.example.pocketdictionary.database.dao.SynonymsDAO;
import com.example.pocketdictionary.database.dao.WordDAO;
import com.example.pocketdictionary.model.Synonyms;
import com.example.pocketdictionary.model.WordEntry;

@Database(entities = {WordEntry.class, Synonyms.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDAO wordDAO();
    public abstract SynonymsDAO synonymsDAO();
    public abstract RhymesDAO rhymesDAO();
    public abstract DefinitionsDAO definitionsDAO();
    public abstract AntonymsDAO antonymsDAO();
}
