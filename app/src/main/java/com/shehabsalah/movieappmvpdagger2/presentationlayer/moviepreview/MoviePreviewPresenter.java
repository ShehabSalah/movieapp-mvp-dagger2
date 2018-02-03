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
package com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview;

import android.support.annotation.Nullable;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.di.modules.MoviePreviewModule;
import com.shehabsalah.movieappmvpdagger2.di.scopes.ActivityScope;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import javax.inject.Inject;

/**
 * Created by ShehabSalah on 1/10/18.
 * Listens to user actions from the UI ({@link MoviePreviewActivity}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the MoviePreviewPresenter (if it fails, it emits a compiler error).  It uses
 * {@link MoviePreviewModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */

@ActivityScope
public class MoviePreviewPresenter implements MoviePreviewContract.presenter{

    @Nullable
    private MoviePreviewContract.View view;
    private MoviesRepository moviesRepository;

    @Inject
    MoviePreviewPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void onFavoritePressed(Movie movie) {
        if (movie.getFavorite() == Constants.FAVORITE_ACTIVE)
            removeFavorite(movie);
        else
           addFavorite(movie);
    }

    private void removeFavorite(Movie movie){
        movie.setFavorite(Constants.FAVORITE_NOT_ACTIVE);
        moviesRepository.updateMovie(movie);
        view.onFavoriteResponse(movie);
    }

    private void addFavorite(Movie movie){
        movie.setFavorite(Constants.FAVORITE_ACTIVE);
        moviesRepository.updateMovie(movie);
        view.onFavoriteResponse(movie);
    }

    @Override
    public void takeView(MoviePreviewContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
