package com.example.pocketdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketdictionary.model.WhatToGet;

public class OfflineActivity extends AppCompatActivity {

    private Spinner dropdown;
    private ArrayAdapter<String> dropdownAdapter;
    private Intent onlineIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        dropdown = findViewById(R.id.detailsSpinnerOffline);
        dropdownAdapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        dropdown.setAdapter(dropdownAdapter);
    }

    public void switchToOnlineMode(View view){
        onlineIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(),"Going online", Toast.LENGTH_LONG).show();
        startActivity(onlineIntent);
    }
}