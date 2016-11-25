package com.example.webprog26.contentproviders;

import android.net.Uri;

/**
 * Created by webprog26 on 25.11.2016.
 */

public final class MovieContract {

    public static final String CONTENT_AUTHORITY =
            "com.example.webprog26.contentproviders.moviedatabase";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_GENRE = "genre";
}
