/*
 * Copyright (C) 2018 Shehab Salah Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shehabsalah.movieappmvpdagger2.util;

/**
 * Created by ShehabSalah on 1/8/18.
 * This class contain all constants variables that will be used among this application.
 */

public class Constants {

    public static final int FAVORITE_ACTIVE             = 1;
    public static final int FAVORITE_NOT_ACTIVE         = 0;
    //API KEY
    public static final String API_KEY                  = "API_KEY"; // <== TODO! (Add TheMovieDB API KEY HERE)

    //LINKS
    public static final String BASE_URL                 = "http://api.themoviedb.org/3/movie/";
    public static final String IMAGE_URL                = "http://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_URL             = "http://image.tmdb.org/t/p/w780";
    public static final String YOUTUBE_BASE_URL         = "http://img.youtube.com/vi/";
    public static final String HIGH_RES_YOUTUBE_IMG     = "/maxresdefault.jpg";
    public static final String FILE_SEPARATOR           = "/";

    //PARAMS
    public static final String PARAM_API_KEY            = "api_key";

    //PAGES
    public static final String PAGE_POPULAR             = "popular";
    public static final String PAGE_TOP_RATED           = "top_rated";
    public static final String PAGE_REVIEWS             = "reviews";
    public static final String PAGE_TRAILERS            = "videos";

    //EXTRAS
    public static final String MOVIE_EXTRA              = "movie";

    //TRANSACTIONS
    public static final String KEY_CONNECTION_TITLE     = "KEY_CONNECTION_NAME";
    public static final String KEY_CONNECTION_IMAGE     = "KEY_CONNECTION_IMAGE";
    public static final String KEY_CONNECTION_CONTAINER = "KEY_CONNECTION_CONTAINER";
}
