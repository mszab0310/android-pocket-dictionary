package com.example.pocketdictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.database.dao.SynonymsDAO;
import com.example.pocketdictionary.database.dao.WordDAO;
import com.example.pocketdictionary.model.WhatToGet;

public class MainActivity extends AppCompatActivity {

    private Spinner detailsDropdown;
    private WordDAO wordDAO;
    private SynonymsDAO synonymsDAO;
    private AppDatabase db;
    private ArrayAdapter<String> dropdownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailsDropdown = findViewById(R.id.detailDropdown);

        //database init
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dictionary").build();
        wordDAO = db.wordDAO();
        synonymsDAO = db.synonymsDAO();
        dropdownAdapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        detailsDropdown.setAdapter(dropdownAdapter);
    }
}