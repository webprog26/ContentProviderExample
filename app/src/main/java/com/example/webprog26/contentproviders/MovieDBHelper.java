package com.example.webprog26.contentproviders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by webprog26 on 25.11.2016.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieList.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        addGenreTable(sqLiteDatabase);
        addMovieTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

    private void addGenreTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table " + GenreEntry.TABLE_NAME
        + " (" + GenreEntry._ID + " integer primary key, "
        + GenreEntry.COLUMN_NAME + " text unique not null);");
    }

    private void addMovieTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table " + MovieEntry.TABLE_NAME + " ("
        + MovieEntry._ID + " integer primary key, "
        + MovieEntry.COLUMN_NAME + " text not null, "
        + MovieEntry.COLUMN_RELEASE_DATE + " text not null, "
        + MovieEntry.MOVIE_GENRE + " integer not null, "
        + "foreign key (" + MovieEntry.MOVIE_GENRE + ") "
        + "references (" + GenreEntry.TABLE_NAME
        + " (" + GenreEntry._ID + "));");
    }
}
