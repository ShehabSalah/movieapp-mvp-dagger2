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

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.di.modules.MovieModule;
import com.shehabsalah.movieappmvpdagger2.di.scopes.ActivityScope;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.models.response.GeneralResponse;
import com.shehabsalah.movieappmvpdagger2.models.response.MoviesResponse;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails.DetailsActivity;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview.MoviePreviewActivity;
import com.shehabsalah.movieappmvpdagger2.util.Constants;
import java.util.ArrayList;


import javax.inject.Inject;

import static android.support.v4.app.ActivityOptionsCompat.*;

/**
 * Created by ShehabSalah on 1/8/18.
 * Listens to user actions from the UI ({@link MoviesListFragment}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the MoviesPresenter (if it fails, it emits a compiler error).  It uses
 * {@link MovieModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */

@ActivityScope
public class MoviesPresenter implements MoviesContract.Presenter {
    @Nullable
    private MoviesContract.View views;
    private MoviesFilter filter;
    private MoviesRepository moviesRepository;
    private boolean setAdapter = true;
    private boolean forceUpdate;
    @Nullable
    private Activity activity;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    MoviesPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void goToDetailsActivity(Movie movie, View imageView, View textView) {
        if (movie != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair<View, String> imagePair = Pair.create(imageView, Constants.KEY_CONNECTION_IMAGE);
                //noinspection unchecked
                ActivityOptionsCompat options = makeSceneTransitionAnimation(activity,
                         imagePair);
                activity.startActivity(
                        new Intent(activity, DetailsActivity.class).putExtra(Constants.MOVIE_EXTRA, movie),
                        options.toBundle()
                );
            } else {
                activity.startActivity(new Intent(activity, DetailsActivity.class).putExtra(Constants.MOVIE_EXTRA, movie));
            }

        }
    }

    @Override
    public void loadMovies() {
        startLoading(filter, forceUpdate, true);
    }

    private void startLoading(MoviesFilter movieType, boolean forceUpdate, boolean initialCall){
        if (forceUpdate && movieType != MoviesFilter.FAVORITES)
            moviesRepository.refreshData();

        moviesRepository.getMovies(new MoviesDataSource.LoadMoviesCallback() {
            @Override
            public void noInternetConnection() {
                views.showNoInternetConnection();
                onMoviesNotAvailable();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                if (movieType == MoviesFilter.MOST_POPULAR) {
                    moviesRepository.deleteAllMovies();
                    moviesRepository.saveMostPopularMovies(((MoviesResponse) response).getResults());
                    startLoading(movieType, false, false);
                    startLoading(MoviesFilter.TOP_RATED, true, false);
                } else if (movieType == MoviesFilter.TOP_RATED) {
                    moviesRepository.saveTopRatedMovies(((MoviesResponse) response).getResults());
                    if (initialCall)
                        startLoading(movieType, false, false);
                }
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                views.showServerError(response.getMessage());
            }

            @Override
            public void onMoviesLoaded(ArrayList<Movie> movies) {
                if (movies.size() > 0) {
                    views.showMovies(movies, setAdapter);
                } else {
                    views.showNoMovies();
                }
            }

            @Override
            public void onMoviesNotAvailable() {
                switch (movieType) {
                    case MOST_POPULAR:
                    case TOP_RATED:
                        views.showNoMovies();
                        break;
                    case FAVORITES:
                        views.showNoFavorites();
                        break;
                }
            }
        }, movieType);

    }

    @Override
    public void setAdvancedInit(MoviesFilter filter, boolean setAdapter, boolean forceUpdate) {
        this.filter = filter;
        this.setAdapter = setAdapter;
        this.forceUpdate = forceUpdate;
    }

    @Override
    public void setBasicInit(boolean setAdapter, boolean forceUpdate) {
        this.setAdapter = setAdapter;
        this.forceUpdate = forceUpdate;
    }

    @Override
    public MoviesFilter getMoviesType() {
        return filter;
    }

    @Override
    public void openMoviePreview(Movie movie, View imageView, View textView, View cardView) {
        if (movie != null) {
            views.makeBackgroundBlur();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair<View, String> titlePair = Pair.create(textView, Constants.KEY_CONNECTION_TITLE);
                Pair<View, String> imagePair = Pair.create(imageView, Constants.KEY_CONNECTION_IMAGE);
                Pair<View, String> containerPair = Pair.create(cardView, Constants.KEY_CONNECTION_CONTAINER);
                //noinspection unchecked
                ActivityOptionsCompat options = makeSceneTransitionAnimation(activity,
                                titlePair, imagePair, containerPair);
                activity.startActivity(
                        new Intent(activity, MoviePreviewActivity.class).putExtra(Constants.MOVIE_EXTRA, movie),
                        options.toBundle()
                );
            } else {
                activity.startActivity(new Intent(activity, MoviePreviewActivity.class).putExtra(Constants.MOVIE_EXTRA, movie));
            }
        }
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void takeView(MoviesContract.View view) {
        this.views = view;
    }

    @Override
    public void dropView() {
        this.views = null;
    }
}