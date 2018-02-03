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
package com.shehabsalah.movieappmvpdagger2.di;

import android.app.Application;

import com.shehabsalah.movieappmvpdagger2.ApplicationClass;
import com.shehabsalah.movieappmvpdagger2.datalayer.MoviesRepository;
import com.shehabsalah.movieappmvpdagger2.di.modules.ActivityBindingModule;
import com.shehabsalah.movieappmvpdagger2.di.modules.MoviesRepositoryModule;
import com.shehabsalah.movieappmvpdagger2.di.modules.PicassoModule;
import com.shehabsalah.movieappmvpdagger2.util.PicassoHandler;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by shehabsalah on 2/2/18.
 * This is a Dagger component. Refer to {@link ApplicationClass} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link ApplicationClass}.
 * {@link AndroidSupportInjectionModule}
 * is the module from Dagger.Android that helps with the generation and location of subComponents.
 */

@Singleton
@Component(modules = {MoviesRepositoryModule.class,
        PicassoModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ApplicationClass> {

    MoviesRepository getMoviesRepository();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
