package com.example.pocketdictionary.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
import com.example.pocketdictionary.model.WordDetailType;
import com.example.pocketdictionary.model.WordEntry;

public class DatabaseQueryService {
    private WordDAO wordDAO;
    private SynonymsDAO synonymsDAO;
    private AntonymsDAO antonymsDAO;
    private RhymesDAO rhymesDAO;
    private DefinitionsDAO definitionsDAO;
    private AppDatabase appDatabase;
    private static final String TAG = "DatabaseQueryService";


    public DatabaseQueryService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        wordDAO = appDatabase.wordDAO();
        synonymsDAO = appDatabase.synonymsDAO();
        antonymsDAO = appDatabase.antonymsDAO();
        rhymesDAO = appDatabase.rhymesDAO();
        definitionsDAO = appDatabase.definitionsDAO();
    }


    public void saveToDatabase(WordEntry wordEntry, WordDetailType wordDetail, String lowerCaseQuery) {
        class ExecuteSave extends AsyncTask<Void,Void,Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                String query = lowerCaseQuery.substring(0,1).toUpperCase() + lowerCaseQuery.substring(1);
                WordEntry existent = wordDAO.findByName(wordEntry.getWord());
                long wordID;
                if(existent != null){
                    wordID = existent.getId();
                }else{
                    wordID = wordDAO.insert(wordEntry);
                }
                switch (query) {
                    case WhatToGet.ANTONYMS:
                        Antonyms antonyms = (Antonyms) wordDetail;
                        antonyms.setWordId(wordID);
                        Log.i(TAG, "doInBackground: antonyms " + antonyms);
                        antonymsDAO.insert(antonyms);
                        break;
                    case WhatToGet.SYNONYMS:
                        Synonyms synonyms = (Synonyms) wordDetail;
                        synonyms.setWordId(wordID);
                        Log.i(TAG, "doInBackground: Synonyms " + synonyms);
                        synonymsDAO.insert(synonyms);
                        break;
                    case WhatToGet.DEFINITIONS:
                        Definitions definitions = (Definitions) wordDetail;
                        definitions.setWordId(wordID);
                        Log.i(TAG, "doInBackground: Definitions " + definitions);
                        definitionsDAO.insert(definitions);
                        break;
                    case WhatToGet.RHYMES:
                        Rhymes rhymes = (Rhymes) wordDetail;
                        rhymes.setWordId(wordID);
                        Log.i(TAG, "doInBackground: Rhymes " + rhymes);
                        rhymesDAO.insert(rhymes);
                        break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
            }
        }
        new ExecuteSave().execute();
    }



}
