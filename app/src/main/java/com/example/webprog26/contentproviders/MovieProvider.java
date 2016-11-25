package com.example.webprog26.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by webprog26 on 25.11.2016.
 */

public class MovieProvider extends ContentProvider {

    private static final int GENRE = 100;
    private static final int GENRE_ID = 101;

    private static final int MOVIE = 200;
    private static final int MOVIE_ID = 201;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    public static UriMatcher buildUriMatcher(){
        String content = MovieContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, MovieContract.PATH_GENRE, GENRE);
        matcher.addURI(content, MovieContract.PATH_GENRE + "/#", GENRE_ID);
        matcher.addURI(content, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(content, MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
        return matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case GENRE:
                return GenreEntry.CONTENT_TYPE;
            case GENRE_ID:
                return GenreEntry.CONTENT_ITEM_TYPE;
            case MOVIE:
                return MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case GENRE:
                retCursor = db.query(GenreEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case GENRE_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(GenreEntry.TABLE_NAME,
                        projection,
                        GenreEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE:
                retCursor = db.query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(MovieEntry.TABLE_NAME,
                        projection,
                        MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch (sUriMatcher.match(uri)){
            case GENRE:
                _id = db.insert(GenreEntry.TABLE_NAME, null, contentValues);

                if(_id > 0){
                    returnUri = GenreEntry.buildGenreUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            case MOVIE:
                _id = db.insert(MovieEntry.TABLE_NAME, null, contentValues);
                if(_id > 0){
                    returnUri = MovieEntry.buildMovieUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows;

        switch ((sUriMatcher.match(uri))){
            case GENRE:
                rows = db.delete(GenreEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE:
                rows = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows;

        switch (sUriMatcher.match(uri)){
            case GENRE:
                rows = db.update(GenreEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case MOVIE:
                rows = db.update(MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }
}
