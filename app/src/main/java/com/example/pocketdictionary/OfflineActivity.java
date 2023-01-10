package com.example.pocketdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pocketdictionary.model.WhatToGet;

public class OfflineActivity extends AppCompatActivity {

    private Spinner dropdown;
    private ArrayAdapter<String> dropdownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        dropdown = findViewById(R.id.detailsSpinnerOffline);
        dropdownAdapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        dropdown.setAdapter(dropdownAdapter);
    }
}