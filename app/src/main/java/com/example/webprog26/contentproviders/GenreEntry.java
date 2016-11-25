package com.example.webprog26.contentproviders;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.webprog26.contentproviders.MovieContract.BASE_CONTENT_URI;
import static com.example.webprog26.contentproviders.MovieContract.PATH_GENRE;

/**
 * Created by webprog26 on 25.11.2016.
 */

public final class GenreEntry implements BaseColumns {

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRE).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/"
                    + CONTENT_URI + "/"
                    + PATH_GENRE;

    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/"
            + CONTENT_URI + "/"
            + PATH_GENRE;

    public static final String TABLE_NAME = "genreTable";
    public static final String COLUMN_NAME = "genreName";

    public static Uri buildGenreUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
