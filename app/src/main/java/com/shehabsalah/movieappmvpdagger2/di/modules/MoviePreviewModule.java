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
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview.MoviePreviewActivity;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview.MoviePreviewContract;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview.MoviePreviewPresenter;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by shehabsalah on 2/2/18.
 * This is a Dagger module. We use this to auto create the MoviePreviewSubComponent and bind
 * the {@link MoviePreviewPresenter} to the graph
 */

@Module
public abstract class MoviePreviewModule {
    // Rather than having the activity deal with getting the intent extra and passing it to the presenter
    // we will provide the taskId directly into the MoviePreviewActivitySubcomponent
    // which is what gets generated for us by Dagger.Android.
    // We can then inject our MOVIE_EXTRA and state into our Presenter without having pass through dependency from
    // the Activity. Each UI object gets the dependency it needs and nothing else.
    @Provides
    @ActivityScope
    static Movie provideMovie(MoviePreviewActivity activity) {
        return activity.getIntent().getParcelableExtra(Constants.MOVIE_EXTRA);
    }

    @ActivityScope
    @Binds
    abstract MoviePreviewContract.presenter previewPresenter(MoviePreviewPresenter previewPresenter);
    //NOTE:  IF you want to have something be only in the Fragment scope but not activity mark a
    //@provides or @Binds method as @FragmentScope.  Use case is when there are multiple fragments
    //in an activity but you do not want them to share all the same objects.

}
