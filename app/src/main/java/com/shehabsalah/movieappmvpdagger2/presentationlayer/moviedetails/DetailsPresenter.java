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
package com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesDataSource;
import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.di.modules.MovieDetailsModule;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.models.MovieReviews;
import com.shehabsalah.movieappmvpdagger2.models.MovieTrailers;
import com.shehabsalah.movieappmvpdagger2.models.response.GeneralResponse;
import com.shehabsalah.movieappmvpdagger2.models.response.ReviewsResponse;
import com.shehabsalah.movieappmvpdagger2.models.response.TrailersResponse;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by ShehabSalah on 1/12/18.
 * Listens to user actions from the UI ({@link DetailsFragment}), retrieves the data and updates the
 * UI as required.
 * <p>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the DetailsPresenter (if it fails, it emits a compiler error). It uses
 * {@link MovieDetailsModule} to do so.
 * <p>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */

public class DetailsPresenter implements DetailsContract.presenter{

    @Nullable
    private DetailsContract.View view;
    @Nullable
    private Activity activity;

    private MoviesRepository moviesRepository;

    @Inject
    DetailsPresenter(MoviesRepository moviesRepository) {
        this.moviesRepository           = moviesRepository;
    }

    @Override
    public void loadMovieInformation(int movieId) {
        loadTrailers(movieId);
        loadReviews(movieId);
    }

    private void loadTrailers(final int movieId){
        moviesRepository.getTrailers(new MoviesDataSource.LoadTrailersCallback() {
            @Override
            public void onTrailersLoaded(ArrayList<MovieTrailers> trailers) {
                if (trailers.size() > 0) {
                    view.showTrailers(trailers);
                } else {
                    view.hideTrailers();
                }
            }

            @Override
            public void onTrailersNotAvailable() {
                view.hideTrailers();
            }

            @Override
            public void noInternetConnection() {
                onTrailersNotAvailable();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                moviesRepository.deleteMovieTrailers(movieId);
                TrailersResponse trailersResponse = (TrailersResponse) response;
                moviesRepository.saveMovieTrailers(trailersResponse.getResults(), movieId);
                ArrayList<MovieTrailers> movieTrailers = trailersResponse.getResults();
                if (movieTrailers.size() > 0) {
                    view.showTrailers(movieTrailers);
                } else {
                    view.hideTrailers();
                }
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                onTrailersNotAvailable();
            }
        }, movieId);
    }

    private void loadReviews(final int movieId){


        moviesRepository.getReviews(new MoviesDataSource.LoadReviewsCallback() {
            @Override
            public void onReviewsLoaded(ArrayList<MovieReviews> reviews) {
                if (reviews.size() > 0) {
                    view.showReviews(reviews);
                } else {
                    view.hideReviews();
                }
            }

            @Override
            public void onReviewsNotAvailable() {
                view.hideReviews();
            }

            @Override
            public void noInternetConnection() {
                onReviewsNotAvailable();
            }

            @Override
            public void onResponse(String TAG, Object response) {
                moviesRepository.deleteMovieReviews(movieId);
                ReviewsResponse reviewsResponse = (ReviewsResponse) response;
                moviesRepository.saveMovieReviews(reviewsResponse.getResults(), movieId);
                ArrayList<MovieReviews> movieReviews = reviewsResponse.getResults();
                if (movieReviews.size() > 0) {
                    view.showReviews(movieReviews);
                } else {
                    view.hideReviews();
                }
            }

            @Override
            public void onErrorResponse(String TAG, GeneralResponse response) {
                onReviewsNotAvailable();
            }
        }, movieId);
    }



    @Override
    public void onFavoriteClick(Movie movie) {
        if (movie.getFavorite() == Constants.FAVORITE_ACTIVE)
            removeFavorite(movie);
        else
            addFavorite(movie);
    }

    @Override
    public void onTrailerClicked(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            activity.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            activity.startActivity(webIntent);
        }
    }

    private void removeFavorite(Movie movie){
        movie.setFavorite(Constants.FAVORITE_NOT_ACTIVE);
        moviesRepository.updateMovie(movie);
        view.favoriteResponse(movie);
    }

    private void addFavorite(Movie movie){
        movie.setFavorite(Constants.FAVORITE_ACTIVE);
        moviesRepository.updateMovie(movie);
        view.favoriteResponse(movie);
    }

    @Override
    public void takeView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
