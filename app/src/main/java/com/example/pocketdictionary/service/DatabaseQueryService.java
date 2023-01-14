package com.example.pocketdictionary.service;

import android.content.Context;

import com.example.pocketdictionary.database.AppDatabase;
import com.example.pocketdictionary.database.dao.AntonymsDAO;
import com.example.pocketdictionary.database.dao.DefinitionsDAO;
import com.example.pocketdictionary.database.dao.RhymesDAO;
import com.example.pocketdictionary.database.dao.SynonymsDAO;
import com.example.pocketdictionary.database.dao.WordDAO;
import com.example.pocketdictionary.exceptions.DatabaseException;
import com.example.pocketdictionary.model.Antonyms;
import com.example.pocketdictionary.model.Definitions;
import com.example.pocketdictionary.model.Rhymes;
import com.example.pocketdictionary.model.Synonyms;
import com.example.pocketdictionary.model.WhatToGet;
import com.example.pocketdictionary.model.WordDetailType;
import com.example.pocketdictionary.model.WordEntry;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private WordDAO wordDAO;
    private SynonymsDAO synonymsDAO;
    private AntonymsDAO antonymsDAO;
    private RhymesDAO rhymesDAO;
    private DefinitionsDAO definitionsDAO;
    private AppDatabase appDatabase;


    public DatabaseQueryService(Context context) {
        appDatabase = AppDatabase.getInstance(context);
        wordDAO = appDatabase.wordDAO();
        synonymsDAO = appDatabase.synonymsDAO();
        antonymsDAO = appDatabase.antonymsDAO();
        rhymesDAO = appDatabase.rhymesDAO();
        definitionsDAO = appDatabase.definitionsDAO();
    }

    public void saveToDatabase(WordEntry wordEntry, WordDetailType wordDetail, String query) {
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

    public List<String> getWordAndDetail(String word, String query) throws DatabaseException {
        WordEntry wordEntry = wordDAO.findByName(word);
        if (wordEntry == null) {
            throw new DatabaseException(DatabaseException.WORD_NOT_FOUND);
        } else {
            List<String> result = new ArrayList<>();
            switch (query) {
                case WhatToGet.ANTONYMS:
                    List<Antonyms> antonymsList = antonymsDAO.getAntonymsForWord(wordEntry.getId());
                    for (Antonyms antonym : antonymsList) {
                        result.add(antonym.getString());
                    }
                    return result;

                case WhatToGet.SYNONYMS:
                    List<Synonyms> synonymsList = synonymsDAO.getSynonymsForWord(wordEntry.getId());
                    for (Synonyms synonym : synonymsList) {
                        result.add(synonym.getString());
                    }
                    return result;

                case WhatToGet.DEFINITIONS:
                    List<Definitions> definitionsList = definitionsDAO.getDefinitionsForWord(wordEntry.getId());
                    for (Definitions definition : definitionsList) {
                        result.add(definition.getString());
                    }
                    return result;

                case WhatToGet.RHYMES:
                    List<Rhymes> rhymesList = rhymesDAO.getRhymesForWord(wordEntry.getId());
                    for (Rhymes rhyme : rhymesList) {
                        result.add(rhyme.getString());
                    }
                    return result;

            }
            return result;
        }
    }
}
