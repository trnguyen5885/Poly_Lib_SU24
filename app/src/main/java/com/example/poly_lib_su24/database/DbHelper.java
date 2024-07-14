package com.example.poly_lib_su24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "booksmanager", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        create table books
        (
            id text primary key autoincrement,
            booktitle text,
            price text,
            type  text
        )
         */
        /*
        create table booktype
        (
            id integer  primary key autoincrement,
            name text
        )

         */
        String sqlbooks = "create table books\n" +
                "        (\n" +
                "            id integer primary key AUTOINCREMENT,\n" +
                "            title text,\n" +
                "            type  text,\n" +
                "            price text\n" +
                "        )";
        db.execSQL(sqlbooks);

        String sqlbooktype = "create table booktype\n" +
                "        (\n" +
                "            id integer primary key autoincrement,\n" +
                "            name text\n" +
                "        )";
        db.execSQL(sqlbooktype);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists books");
        db.execSQL("drop table if exists booktype");
        onCreate(db);
    }
}
