package com.example.pocketdictionary;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.database.dao.AntonymsDAO;
import com.example.pocketdictionary.database.dao.DefinitionsDAO;
import com.example.pocketdictionary.database.dao.RhymesDAO;
import com.example.pocketdictionary.database.dao.SynonymsDAO;
import com.example.pocketdictionary.database.dao.WordDAO;
import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.Definitions;
import com.example.pocketdictionary.model.Rhymes;
import com.example.pocketdictionary.model.Synonyms;
import com.example.pocketdictionary.model.WhatToGet;
import com.example.pocketdictionary.model.WordEntry;
import com.example.pocketdictionary.service.DatabaseQueryService;
import com.example.pocketdictionary.service.NightModeService;

import java.util.ArrayList;
import java.util.List;

public class OfflineActivity extends AppCompatActivity {

    private Spinner dropdown;
    private ArrayAdapter<String> dropdownAdapter;
    private Intent onlineIntent;
    private EditText searchbar;
    private Button searchButton;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private WordDAO wordDAO;
    private SynonymsDAO synonymsDAO;
    private AntonymsDAO antonymsDAO;
    private RhymesDAO rhymesDAO;
    private DefinitionsDAO definitionsDAO;
    private AppDatabase appDatabase;
    private NightModeService nightModeService;
    private boolean hasLightSensor;

    private String word;
    private String query;
    private DatabaseQueryService databaseQueryService;
    private static final String TAG = "OfflineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        wordDAO = appDatabase.wordDAO();
        synonymsDAO = appDatabase.synonymsDAO();
        antonymsDAO = appDatabase.antonymsDAO();
        rhymesDAO = appDatabase.rhymesDAO();
        definitionsDAO = appDatabase.definitionsDAO();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            hasLightSensor = true;
            nightModeService = new NightModeService(getApplicationContext(), 20000);
            nightModeService.start();
        } else {
            hasLightSensor = false;
        }

        dropdown = findViewById(R.id.detailsSpinnerOffline);
        dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        dropdown.setAdapter(dropdownAdapter);
        searchbar = findViewById(R.id.offlineWordSearchTextEdit);
        searchButton = findViewById(R.id.searchOfflineButton);
        listView = findViewById(R.id.offlineListView);
        databaseQueryService = new DatabaseQueryService(getApplicationContext());
    }

    public void switchToOnlineMode(View view) {
        onlineIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), "Going online", Toast.LENGTH_LONG).show();
        if (hasLightSensor)
            nightModeService.stop();
        startActivity(onlineIntent);
    }

    public void searchOfflineButton(View view) {
        word = searchbar.getText().toString();
        query = dropdown.getSelectedItem().toString();
        Log.i(TAG, "searchOfflineButton: " + word + " " + query);
        new ExecuteQuery().execute();
    }

    class ExecuteQuery extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground: Started background query thread");
            WordEntry wordEntry = wordDAO.findByName(word);
            if (wordEntry == null) {
                return new ArrayList<>();
            } else {
                Log.i(TAG, "doInBackground: " + wordEntry.getWord());
                List<String> result = new ArrayList<>();
                String lowercaseQuery = query.substring(0, 1).toUpperCase() + query.substring(1);
                switch (lowercaseQuery) {
                    case WhatToGet.ANTONYMS:
                        List<Antonyms> antonymsList = antonymsDAO.getAntonymsForWord(wordEntry.getId());
                        if (antonymsList.size() > 0) {
                            for (Antonyms antonym : antonymsList) {
                                result.add(antonym.getString());
                            }
                            return result;
                        } else break;
                    case WhatToGet.SYNONYMS:
                        List<Synonyms> synonymsList = synonymsDAO.getSynonymsForWord(wordEntry.getId());
                        if (synonymsList.size() > 0) {
                            for (Synonyms synonym : synonymsList) {
                                result.add(synonym.getString());
                            }
                            return result;
                        } else break;
                    case WhatToGet.DEFINITIONS:
                        List<Definitions> definitionsList = definitionsDAO.getDefinitionsForWord(wordEntry.getId());
                        if (definitionsList.size() > 0) {
                            for (Definitions definition : definitionsList) {
                                result.add(definition.getString());
                            }
                            return result;
                        } else break;
                    case WhatToGet.RHYMES:
                        List<Rhymes> rhymesList = rhymesDAO.getRhymesForWord(wordEntry.getId());
                        if (rhymesList.size() > 0) {
                            for (Rhymes rhyme : rhymesList) {
                                result.add(rhyme.getString());
                            }
                            return result;
                        } else break;
                }
                Log.i(TAG, "doInBackground: No query found");
                return result;
            }
        }

        @Override
        protected void onPostExecute(List<String> details) {
            Log.i(TAG, "onPostExecute: The details length is" + details.size());
            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
            listView.setAdapter(arrayAdapter);
            arrayAdapter.addAll(details);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}