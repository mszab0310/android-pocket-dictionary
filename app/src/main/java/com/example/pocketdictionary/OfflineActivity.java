package com.example.pocketdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.model.WhatToGet;
import com.example.pocketdictionary.model.WordDetailType;

import java.util.List;

public class OfflineActivity extends AppCompatActivity {

    private Spinner dropdown;
    private ArrayAdapter<String> dropdownAdapter;
    private Intent onlineIntent;
    private EditText searchbar;
    private Button searchButton;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<WordDetailType> wordDetailTypeArrayList;
    private AppDatabase appDatabase;
    private String word;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        dropdown = findViewById(R.id.detailsSpinnerOffline);
        dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        dropdown.setAdapter(dropdownAdapter);
        searchbar = findViewById(R.id.offlineWordSearchTextEdit);
        searchButton = findViewById(R.id.searchOfflineButton);
        listView = findViewById(R.id.offlineListView);
        appDatabase = AppDatabase.getInstance(getApplicationContext());
    }

    public void switchToOnlineMode(View view) {
        onlineIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), "Going online", Toast.LENGTH_LONG).show();
        startActivity(onlineIntent);
    }

    public void searchOfflineButton(View view){
        word = searchbar.getText().toString();
        query = dropdown.getSelectedItem().toString();


    }
}