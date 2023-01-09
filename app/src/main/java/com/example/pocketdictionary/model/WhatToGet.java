package com.example.pocketdictionary.model;

import java.util.Arrays;
import java.util.List;

public class WhatToGet {
    public static final String DEFINITIONS = "definitions";
    public static final String SYNONYMS = "synonyms";
    public static final String ANTONYMS = "antonyms";
    public static final String RHYMES = "rhymes";

    public static List<String> getListOfPossibilities(){
        return Arrays.asList(DEFINITIONS,SYNONYMS,ANTONYMS,RHYMES);
    }
}
