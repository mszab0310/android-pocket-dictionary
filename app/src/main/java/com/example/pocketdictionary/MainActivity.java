package com.example.pocketdictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.database.WordDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //database init
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dictionary").build();
        WordDAO wordDAO = db.wordDAO();
    }
}