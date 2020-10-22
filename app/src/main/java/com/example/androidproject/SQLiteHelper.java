package com.example.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="test.db";
    public static final int    DB_VERSION=1;
    SQLiteDatabase database;
    private Context context;
    public SQLiteHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
