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
package com.shehabsalah.movieappmvpdagger2.datalayer.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.models.MovieReviews;
import com.shehabsalah.movieappmvpdagger2.models.MovieTrailers;

/**
 * Created by ShehabSalah on 1/9/18.
 * The Room Database that contains the application tables.
 */
@Database(entities = {Movie.class, MovieReviews.class, MovieTrailers.class}, version = 1, exportSchema = false)
public abstract class MovieAppDatabase extends RoomDatabase {

    public abstract MovieDAO movieDAO();
}
