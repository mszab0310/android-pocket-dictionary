package com.example.pocketdictionary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.database.dao.AntonymsDAO;
import com.example.pocketdictionary.database.dao.DefinitionsDAO;
import com.example.pocketdictionary.database.dao.RhymesDAO;
import com.example.pocketdictionary.database.dao.SynonymsDAO;
import com.example.pocketdictionary.database.dao.WordDAO;
import com.example.pocketdictionary.model.WhatToGet;
import com.example.pocketdictionary.util.InternetCheck;

public class MainActivity extends AppCompatActivity {

    private Spinner detailsDropdown;
    private WordDAO wordDAO;
    private SynonymsDAO synonymsDAO;
    private AntonymsDAO antonymsDAO;
    private RhymesDAO rhymesDAO;
    private DefinitionsDAO definitionsDAO;
    private AppDatabase db;
    private ArrayAdapter<String> dropdownAdapter;
    private AlertDialog.Builder alertBuilder;
    private static final String TAG = "MainActivity";
    private Intent offlineIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailsDropdown = findViewById(R.id.detailDropdown);

        //database init
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "dictionary-database").build();
        wordDAO = db.wordDAO();
        synonymsDAO = db.synonymsDAO();
        antonymsDAO = db.antonymsDAO();
        rhymesDAO = db.rhymesDAO();
        definitionsDAO = db.definitionsDAO();

        dropdownAdapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        detailsDropdown.setAdapter(dropdownAdapter);
        alertBuilder = new AlertDialog.Builder(this);
        
        //checks whether there is an active internet connection or not
        offlineIntent = new Intent(this, OfflineActivity.class);
        if (InternetCheck.isInternetAvailable(this)) {
            Toast.makeText(getApplicationContext(),"Internet connection established!", Toast.LENGTH_SHORT).show();
        } else {
            alertBuilder.setTitle("No internet connection!").setMessage("Do you want to use offline or online dictionary?")
                    .setCancelable(false)
                    .setPositiveButton("Online", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO: stay on online activity page
                            Log.i(TAG, "onClick: Online");
                        }
                    }).setNegativeButton("Offline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(offlineIntent);
                            Log.i(TAG, "onClick: offline");
                        }
                    }).show();
        }
    }

    public void switchToOfflineMode(View view){
        Toast.makeText(getApplicationContext(),"Going offline", Toast.LENGTH_LONG).show();
        startActivity(offlineIntent);
    }


}