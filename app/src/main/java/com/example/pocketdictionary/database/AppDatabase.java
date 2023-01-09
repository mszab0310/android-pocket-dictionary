package com.example.pocketdictionary.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.pocketdictionary.model.WordEntry;

@Database(entities = {WordEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDAO wordDAO();
}
