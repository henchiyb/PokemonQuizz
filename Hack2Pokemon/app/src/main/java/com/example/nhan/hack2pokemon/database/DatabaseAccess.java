package com.example.nhan.hack2pokemon.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import com.example.nhan.hack2pokemon.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nhan on 9/26/2016.
 */

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static  DatabaseAccess instance;
    private Pokemon pokemon;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null)
            instance = new DatabaseAccess(context);
        return instance;
    }

    public void open(){
        database = openHelper.getWritableDatabase();
    }

    public void close(){
        if(database != null)
            database.close();
    }

    public Pokemon getRandomOnePokemon(List<String> listTagSeen, List<String> listGenPicked){
        String whereTag = "";
        String whereGen = "";

        for(int i=0; i < listTagSeen.size(); i++){
            whereTag += " tag != '" + listTagSeen.get(i) + "' AND ";
        }

        for(int i=0; i < listGenPicked.size(); i++){
            if (i < listGenPicked.size() - 1) {
                whereGen += " gen = " + listGenPicked.get(i) + " AND ";
            } else {
                whereGen += " gen = " + listGenPicked.get(i) + " ";
            }
        }

        Cursor cursor;

        if (listTagSeen.size() == 0){
            cursor = database.rawQuery("SELECT name, tag, img, color FROM pokemon WHERE" + whereGen
                    + "ORDER BY RANDOM() LIMIT 1", null);
        } else {
            cursor = database.rawQuery("SELECT name, tag, img, color FROM pokemon WHERE" + whereTag + whereGen
                    + "ORDER BY RANDOM() LIMIT 1", null);
        }
        cursor.moveToFirst();
        String name = cursor.getString(0);
        String tag = cursor.getString(1);
        String img = cursor.getString(2);
        String color = cursor.getString(3);
        listTagSeen.add(tag);
        pokemon = new Pokemon(name, tag, img, color);
        cursor.close();
        return pokemon;
    }

    public List<String> getListNameForWrongAnswer(List<String> listTagSeen, List<String> listGenPicked){
        String whereTag = "";
        String whereGen = "";

        for(int i=0; i < listTagSeen.size(); i++){
            whereTag += " tag != '" + listTagSeen.get(i) + "' AND ";
        }

        for(int i=0; i < listGenPicked.size(); i++){
            if (i < listGenPicked.size() - 1) {
                whereGen += " gen = " + listGenPicked.get(i) + " AND ";
            } else {
                whereGen += " gen = " + listGenPicked.get(i) + " ";
            }
        }

        List<String> listNameForWrongAnswer = new ArrayList<>();
        Cursor cursor;
        if (listTagSeen.size() == 0){
            cursor = database.rawQuery("SELECT name FROM pokemon WHERE" + whereGen
                    + "ORDER BY RANDOM() LIMIT 3", null);
        } else {
            cursor = database.rawQuery("SELECT name FROM pokemon WHERE" + whereTag + whereGen
                    + "ORDER BY RANDOM() LIMIT 3", null);
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String name = cursor.getString(0);
            listNameForWrongAnswer.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        return listNameForWrongAnswer;
    }

}
