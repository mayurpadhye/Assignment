package com.sindans.assignment.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 05/01/2017.
 */

public class SqlDB extends SQLiteOpenHelper
{

    public static final int version =1;
    public static final String Database="survey.db";

    public SqlDB(Context context)
    {
        super(context,Database,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("Create table survey_form(id INTEGER PRIMARY KEY  AUTOINCREMENT,name text,city text,gender text,first_image_path text,second_image_path text,area text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS survey_form");
        onCreate(db);
    }



}
