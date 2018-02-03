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
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails.DetailsActivity;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails.DetailsFragment;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviepreview.MoviePreviewActivity;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.movieslist.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by shehabsalah on 2/2/18.
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module
 * ActivityBindingModule is on, in our case that will be AppComponent.
 *
 * The beautiful part about this setup is that you never need to tell AppComponent that it is going
 * to have all these subcomponents nor do you need to tell these subcomponents that AppComponent
 * exists.
 *
 * We are also telling Dagger.Android that this generated Subcomponent needs to include the specified
 * modules and be aware of a scope annotation @ActivityScope When Dagger. Android annotation
 * processor runs it will create 3 subcomponents for us.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MovieModule.class})
    abstract MainActivity moviesActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MoviePreviewModule.class})
    abstract MoviePreviewActivity moviePreviewActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MovieDetailsModule.class})
    abstract DetailsActivity detailsActivity();

}
