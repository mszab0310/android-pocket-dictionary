package com.example.pocketdictionary.model;

import java.util.Arrays;
import java.util.List;

public class WhatToGet {
    public static final String DEFINITIONS = "Definitions";
    public static final String SYNONYMS = "Synonyms";
    public static final String ANTONYMS = "Antonyms";
    public static final String RHYMES = "Rhymes";

    public static List<String> getListOfPossibilities(){
        return Arrays.asList(DEFINITIONS,SYNONYMS,ANTONYMS,RHYMES);
    }
}
