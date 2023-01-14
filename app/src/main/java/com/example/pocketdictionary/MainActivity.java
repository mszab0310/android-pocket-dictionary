package com.example.pocketdictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketdictionary.model.WhatToGet;
import com.example.pocketdictionary.model.WordDetailType;
import com.example.pocketdictionary.model.WordEntry;
import com.example.pocketdictionary.service.DatabaseQueryService;
import com.example.pocketdictionary.service.HttpRequestService;
import com.example.pocketdictionary.service.NightModeService;
import com.example.pocketdictionary.util.InternetCheck;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner detailsDropdown;
    private ArrayAdapter<String> dropdownAdapter;
    private AlertDialog.Builder alertBuilder;
    private static final String TAG = "MainActivity";
    private Intent offlineIntent;
    private HttpRequestService httpRequestService;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<WordDetailType> wordDetailTypeArrayList;
    private EditText searchbar;
    private Button searchButton;
    private Context context;
    private ProgressBar progressBar;
    private String word;
    private String query;
    private NightModeService nightModeService;
    private boolean hasLightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detailsDropdown = findViewById(R.id.detailDropdown);
        listView = findViewById(R.id.responseListView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        searchbar = findViewById(R.id.wordInputEditText);
        searchButton = findViewById(R.id.searchOnlineButton);
        context = getApplicationContext();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            hasLightSensor = true;
            nightModeService = new NightModeService(getApplicationContext(), 20000);
            nightModeService.start();
        } else {
            hasLightSensor = false;
        }


        dropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, WhatToGet.getListOfPossibilities());
        detailsDropdown.setAdapter(dropdownAdapter);
        alertBuilder = new AlertDialog.Builder(this);

        //checks whether there is an active internet connection or not
        offlineIntent = new Intent(this, OfflineActivity.class);
        if (InternetCheck.isInternetAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Internet connection established!", Toast.LENGTH_SHORT).show();
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
                            if (hasLightSensor)
                                nightModeService.stop();
                            Log.i(TAG, "onClick: offline");
                        }
                    }).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemClickListener(i);
            }
        });
    }

    public void switchToOfflineMode(View view) {
        Toast.makeText(getApplicationContext(), "Going offline", Toast.LENGTH_LONG).show();
        if (hasLightSensor)
            startActivity(offlineIntent);
        nightModeService.stop();
    }

    public void searchOnlineButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String word = searchbar.getText().toString();
        String query = detailsDropdown.getSelectedItem().toString().toLowerCase();
        Log.i(TAG, "searchOnlineButton: word & query" + word + " " + query);
        new GetDataTask().execute(word, query);
    }

    private class GetDataTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            word = params[0];
            query = params[1];
            httpRequestService = new HttpRequestService();
            try {
                wordDetailTypeArrayList = httpRequestService.getData(word, query);
                return new ArrayList<>();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            progressBar.setVisibility(View.INVISIBLE);
            arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
            List<String> parsedResponse = new ArrayList<>();
            for (WordDetailType entry : wordDetailTypeArrayList) {
                parsedResponse.add(entry.getString());
            }
            listView.setAdapter(arrayAdapter);
            arrayAdapter.addAll(parsedResponse);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void listItemClickListener(int index) {
        alertBuilder.setTitle("Save to offline dictionary?")
                .setMessage("Do you want to save this " + query.substring(0, query.length() - 1) + " to your offline dictionary?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: I got clicked");
                        saveToDatabase(index);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                }).show();
    }

    private void saveToDatabase(int index) {
        Log.i(TAG, "saveToDatabase: Trying to save to db");
        WordEntry wordEntry = new WordEntry(word);
        WordDetailType wordDetailType = wordDetailTypeArrayList.get(index);
        DatabaseQueryService databaseQueryService = new DatabaseQueryService(context);
        databaseQueryService.saveToDatabase(wordEntry, wordDetailType, query);
    }

}