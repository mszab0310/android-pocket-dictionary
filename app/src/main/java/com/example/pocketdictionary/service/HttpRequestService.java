package com.example.pocketdictionary.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.Definitions;
import com.example.pocketdictionary.model.ErrorResponse;
import com.example.pocketdictionary.model.Rhymes;
import com.example.pocketdictionary.model.Synonyms;
import com.example.pocketdictionary.model.WordDetailType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestService {

    private static final String WORDS_API_URL = "https://wordsapiv1.p.rapidapi.com/words";
    private static final String TAG = "HttpRequestService";
    private CountDownLatch latch;
    private AtomicReference<Response> successReference;

    public HttpRequestService() {
    }


    public List<WordDetailType> getData(String word, String query) throws JSONException, InterruptedException, IOException {
        String url = WORDS_API_URL + "/" + word + "/" + query;
        OkHttpClient client = new OkHttpClient();
        Log.i(TAG, "getData: started call to url" + url);

        latch = new CountDownLatch(1);
        successReference = new AtomicReference<>();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", "2150b50475mshc34b37f2f134b0ep18e89bjsn58c15a6ebd56")
                .addHeader("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com")
                .build();
        String responseString;

        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call,  IOException e) {
                        latch.countDown();
                    }

                    @Override
                    public void onResponse( Call call,  Response response) throws IOException {
                        Log.i(TAG, "onResponse: " + new Date().getTime());
                        successReference.set(response);
                        latch.countDown();
                    }
                });
            }
        });
        Log.i(TAG, "getData: start" + new Date().getTime());
        requestThread.start();
        latch.await();
        responseString = Objects.requireNonNull(successReference.get().body()).string();
        JSONObject errorObject = new JSONObject(responseString);
        Log.i(TAG, "getData: response before parsing" + errorObject);
        if( errorObject.has("success") && !errorObject.getBoolean("success")){
            return Collections.singletonList(new ErrorResponse(errorObject.getString("message")));
        }
        Log.i(TAG, "getData:" + responseString);
        switch (query) {
            case "definitions":
                return parseDefinitionsFromString(responseString);
            case "synonyms":
                return parseSynonymsFromString(responseString);
            case "antonyms":
                return parseAntonymsFromString(responseString);
            case "rhymes":
                return parseRhymesFromString(responseString);
            default:
                return Collections.singletonList(new ErrorResponse(responseString));
        }
    }

    private List<WordDetailType> parseDefinitionsFromString(String definitionsResponse) throws JSONException {
        //refer to https://www.wordsapi.com/docs/
        List<WordDetailType> definitionsList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(definitionsResponse);
        JSONArray definitions = jsonObject.getJSONArray("definitions");
        for (int i = 0; i < definitions.length(); i++) {
            JSONObject entry = definitions.getJSONObject(i);
            definitionsList.add(new Definitions(entry.getString("definition"), entry.getString("partOfSpeech")));
        }
        return definitionsList;
    }

    private List<WordDetailType> parseSynonymsFromString(String definitionsResponse) throws JSONException {
        //refer to https://www.wordsapi.com/docs/
        List<WordDetailType> synonymsList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(definitionsResponse);
        JSONArray synonyms = jsonObject.getJSONArray("synonyms");
        for (int i = 0; i < synonyms.length(); i++) {
            String entry = synonyms.getString(i);
            synonymsList.add(new Synonyms(entry));
        }
        return synonymsList;
    }

    private List<WordDetailType> parseAntonymsFromString(String definitionsResponse) throws JSONException {
        //refer to https://www.wordsapi.com/docs/
        List<WordDetailType> antonymsList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(definitionsResponse);
        JSONArray synonyms = jsonObject.getJSONArray("synonyms");
        for (int i = 0; i < synonyms.length(); i++) {
            String entry = synonyms.getString(i);
            antonymsList.add(new Antonyms(entry));
        }
        return antonymsList;
    }

    private List<WordDetailType> parseRhymesFromString(String definitionsResponse) throws JSONException {
        //refer to https://www.wordsapi.com/docs/
        List<WordDetailType> rhymesList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(definitionsResponse);
        JSONObject rhymesJSONObject = jsonObject.getJSONObject("rhymes");
        JSONArray rhymes = rhymesJSONObject.getJSONArray("all");
        for (int i = 0; i < rhymes.length(); i++) {
            String entry = rhymes.getString(i);
            rhymesList.add(new Rhymes(entry));
        }
        return rhymesList;
    }

}
