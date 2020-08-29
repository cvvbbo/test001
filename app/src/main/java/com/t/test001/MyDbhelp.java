package com.t.test001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Author: xiong
 * Time: 2020/8/20 12:11
 */
public class MyDbhelp extends SQLiteOpenHelper {


    public final static String table_name = "work";
    public final static String project_name = "project_name";
    public final static String operator_name = "operator_name";
    public final static String rectification_name = "rectification_name";
    public final static String check_name = "check_name";
    public final static String issue = "issue";
    public final static String operator_time = "operator_time";
    public final static String photo = "photo";
    public final static String DB_name = "mydates.db";

    public MyDbhelp(@Nullable Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + table_name + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                project_name + " TEXT, " +
                operator_name + " TEXT, " +
                rectification_name + " TEXT, " +
                check_name + " TEXT," +
                issue + " TEXT, " +
                operator_time + " TEXT," +
                photo + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
