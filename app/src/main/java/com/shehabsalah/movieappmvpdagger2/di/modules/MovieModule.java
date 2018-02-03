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

import com.shehabsalah.movieappmvpdagger2.di.scopes.ActivityScope;
import com.shehabsalah.movieappmvpdagger2.di.scopes.FragmentScope;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist.MoviesContract;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist.MoviesListFragment;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist.MoviesPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by shehabsalah on 2/2/18.
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link MoviesPresenter}.
 */

@Module
public abstract class MovieModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract MoviesListFragment moviesListFragment();

    @ActivityScope
    @Binds
    abstract MoviesContract.Presenter moviePresenter(MoviesPresenter presenter);
}
