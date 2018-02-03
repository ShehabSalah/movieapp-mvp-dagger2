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
package com.shehabsalah.movieappmvpdagger2.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.local.MovieAppDatabase;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.local.MovieDAO;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.local.MoviesLocalDataSource;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.MoviesRemoteDataSource;
import com.shehabsalah.movieappmvpdagger2.di.scopes.Local;
import com.shehabsalah.movieappmvpdagger2.di.scopes.Remote;
import com.shehabsalah.movieappmvpdagger2.util.AppExecutors;
import com.shehabsalah.movieappmvpdagger2.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by shehabsalah on 2/2/18.
 * This is used by Dagger to inject the required arguments into the {@link MoviesRepository}.
 */
@Module
public abstract class MoviesRepositoryModule {
    private static final int THREAD_COUNT = 3;

    @Singleton
    @Binds
    @Local
    abstract MoviesDataSource provideLocalDataSource(MoviesLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract MoviesDataSource provideRemoteDataSource(MoviesRemoteDataSource dataSource);

    @Singleton
    @Provides
    static MovieAppDatabase provideDb(Application context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                MovieAppDatabase.class, "MoviesAppMvpClean.db")
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    static MovieDAO provideMovieDao(MovieAppDatabase db) {
        return db.movieDAO();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}
