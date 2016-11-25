package com.example.webprog26.contentproviders;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.webprog26.contentproviders.MovieContract.BASE_CONTENT_URI;
import static com.example.webprog26.contentproviders.MovieContract.PATH_MOVIE;

/**
 * Created by webprog26 on 25.11.2016.
 */

public final class MovieEntry implements BaseColumns {

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_MOVIE).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MOVIE;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;

    public static final String TABLE_NAME = "movieTable";
    public static final String COLUMN_NAME = "movieName";
    public static final String COLUMN_RELEASE_DATE = "movieReleaseDate";
    public static final String MOVIE_GENRE = "movieGenre";

    public static Uri buildMovieUri(long id){
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
