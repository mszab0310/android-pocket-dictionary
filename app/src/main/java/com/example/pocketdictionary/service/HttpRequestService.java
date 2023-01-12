package com.example.pocketdictionary.service;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestService {
    private ExecutorService executor;
    private static final String WORDS_API_URL = "https://wordsapiv1.p.mashape.com/words";
    private static final String TAG = "HttpRequestService";

    public HttpRequestService() {
        executor = Executors.newSingleThreadExecutor();
    }

    public void getWordData(String word, String query) {
        executor.submit(new Runnable() {
            public void run() {
                getData(word, query);
            }
        });
    }


    private void getData(String word, String query) {
        String url = WORDS_API_URL + "/" + word + "/" +query;
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        String responseString;
        try (Response response = client.newCall(request).execute()) {
            responseString = response.body().string();
            Log.i(TAG, "getData:" + responseString);
        } catch (IOException e) {
            // Handle exception
        }

    }

}
