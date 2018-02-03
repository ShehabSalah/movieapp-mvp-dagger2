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
package com.shehabsalah.movieappmvpdagger2.datalayer.source.remote;

import android.app.Application;
import android.support.annotation.NonNull;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.apis.MovieApiConfig;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.apis.ReviewsApiConfig;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.apis.TrailersApiConfig;
import com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.request.RequestHandler;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist.MoviesFilter;
import com.shehabsalah.movieappmvpdagger2.ApplicationClass;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by shehabsalah on 1/25/18.
 * Implementation of the data source that calls the APIs via network connections.
 */
@Singleton
public class MoviesRemoteDataSource implements MoviesDataSource {
    private Application application;

    @Inject
    MoviesRemoteDataSource(Application application) {
        this.application = application;
    }


    /**
     * Load movies list that has type most popular.
     *
     * @param callback to notify back the {@link MoviesRepository} with the response result.
     */
    private void loadMostPopular(@NonNull MovieApiConfig movieApiConfig, @NonNull LoadMoviesCallback callback) {
        // Execute the API and passing the callback which implemented in {@link MoviesRepository}.
        // By passing the call back to the execute method, when the server response return, callback
        // will be fired.
        RequestHandler.execute(
                movieApiConfig.executePopular(Constants.API_KEY),
                callback,
                application.getApplicationContext()
        );
    }

    /**
     * Load movies list that has type most popular.
     *
     * @param callback to notify back the {@link MoviesRepository} with the response result.
     */
    private void loadTopRated(@NonNull MovieApiConfig movieApiConfig, @NonNull LoadMoviesCallback callback) {
        // Execute the API and passing the callback which implemented in {@link MoviesRepository}.
        // By passing the call back to the execute method, when the server response return, callback
        // will be fired.
        RequestHandler.execute(
                movieApiConfig.executeTopRated(Constants.API_KEY),
                callback,
                application.getApplicationContext()
        );
    }

    /**
     * Load movies from server.
     *
     * @param callback to notify back the {@link MoviesRepository} with the response result.
     * @param filter   movie type to load from server.
     */
    @Override
    public void getMovies(@NonNull LoadMoviesCallback callback, MoviesFilter filter) {
        MovieApiConfig movieApiConfig = RequestHandler.getClient(Constants.BASE_URL).create(MovieApiConfig.class);
        switch (filter) {
            case TOP_RATED:
                loadTopRated(movieApiConfig, callback);
                break;
            case MOST_POPULAR:
                loadMostPopular(movieApiConfig, callback);
                break;
            default:
                callback.onMoviesNotAvailable();
        }
    }

    /**
     * Load the movie trailers from server.
     *
     * @param callback to notify back the {@link MoviesRepository} with the response result.
     * @param movieId  to load it's trailers.
     */
    @Override
    public void getTrailers(@NonNull LoadTrailersCallback callback, int movieId) {
        TrailersApiConfig trailersApiConfig =
                RequestHandler.getClient(Constants.BASE_URL + String.valueOf(movieId) + Constants.FILE_SEPARATOR)
                        .create(TrailersApiConfig.class);

        // Execute the API and passing the callback which implemented in {@link MoviesRepository}.
        // By passing the call back to the execute method, when the server response return, callback
        // will be fired.
        RequestHandler.execute(
                trailersApiConfig.getMovieTrailers(Constants.API_KEY),
                callback,
                application.getApplicationContext()
        );
    }

    /**
     * Load the movie reviews from server.
     *
     * @param callback to notify back the {@link MoviesRepository} with the response result.
     * @param movieId  to load it's reviews.
     */
    @Override
    public void getReviews(@NonNull LoadReviewsCallback callback, int movieId) {
        ReviewsApiConfig reviewsApiConfig =
                RequestHandler.getClient(Constants.BASE_URL + String.valueOf(movieId) + Constants.FILE_SEPARATOR)
                        .create(ReviewsApiConfig.class);

        // Execute the API and passing the callback which implemented in {@link MoviesRepository}.
        // By passing the call back to the execute method, when the server response return, callback
        // will be fired.
        RequestHandler.execute(
                reviewsApiConfig.getMovieReviews(Constants.API_KEY),
                callback,
                application.getApplicationContext()
        );
    }
}
