package com.example.pocketdictionary.service;

import android.content.Context;

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


    public DatabaseQueryService(Context context){
        appDatabase = AppDatabase.getInstance(context);
        wordDAO = appDatabase.wordDAO();
        synonymsDAO = appDatabase.synonymsDAO();
        antonymsDAO = appDatabase.antonymsDAO();
        rhymesDAO = appDatabase.rhymesDAO();
        definitionsDAO = appDatabase.definitionsDAO();
    }

    public void saveToDatabase(WordEntry wordEntry, WordDetailType wordDetail, String query){
        wordDAO.insert(wordEntry);
        switch (query) {
            case WhatToGet.ANTONYMS:
                antonymsDAO.insert((Antonyms) wordDetail);
                break;
            case WhatToGet.SYNONYMS:
                synonymsDAO.insert((Synonyms) wordDetail);
                break;
            case WhatToGet.DEFINITIONS:
                definitionsDAO.insert((Definitions) wordDetail);
                break;
            case WhatToGet.RHYMES:
                rhymesDAO.insert((Rhymes) wordDetail);
                break;
        }

    }
}
