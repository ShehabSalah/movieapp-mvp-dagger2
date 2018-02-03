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

import com.shehabsalah.movieappmvpdagger2.BasePresenter;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.models.MovieReviews;
import com.shehabsalah.movieappmvpdagger2.models.MovieTrailers;

import java.util.ArrayList;

/**
 * Created by ShehabSalah on 1/12/18.
 * This specifies the contract between the View and the presenter.
 */

public interface DetailsContract {
    interface View {
        void showTrailers(ArrayList<MovieTrailers> movieTrailers);
        void hideTrailers();
        void showReviews(ArrayList<MovieReviews> reviews);
        void hideReviews();
        void favoriteResponse(Movie movie);
    }

    interface presenter extends BasePresenter<View>{
        void loadMovieInformation(int movieId);
        void setActivity(Activity activity);
        void onTrailerClicked(String key);
        void onFavoriteClick(Movie movie);
    }
}
