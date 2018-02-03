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
package com.shehabsalah.movieappmvpdagger2;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class ApplicationClass extends DaggerApplication {
    @Inject
    MoviesRepository moviesRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * We need this method for testing. Actually we didn't implement the test yet, but it will be needed.
     * Our Espresso tests need to be able to get an instance of the {@link MoviesRepository}
     * so that we can delete all movies before running each test
     */
    @VisibleForTesting
    public MoviesRepository getMoviesRepository() {
        return moviesRepository;
    }
}
