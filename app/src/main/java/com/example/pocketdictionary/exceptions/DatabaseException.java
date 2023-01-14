package com.example.pocketdictionary.exceptions;

public class DatabaseException extends Exception{
    public static final String WORD_NOT_FOUND = "Can't find word in database";

    public DatabaseException(String message){
        super(message);
    }
}
