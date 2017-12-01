package com.example.nhan.hack2pokemon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Nhan on 9/26/2016.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "pokemon.db";
    private static final int DB_VER = 1;
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
}
