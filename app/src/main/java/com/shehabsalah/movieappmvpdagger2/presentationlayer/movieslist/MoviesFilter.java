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

package com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist;

/**
 * Created by ShehabSalah on 1/8/18.
 * Used with the movies list menu
 */

public enum MoviesFilter {
    /**
     * Load most popular movies.
     * */
    MOST_POPULAR,
    /**
     * Load top rated movies.
     * */
    TOP_RATED,
    /**
     * Load user favorites movies.
     * */
    FAVORITES
}