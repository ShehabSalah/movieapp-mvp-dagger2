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

import android.app.Application;

import com.shehabsalah.movieappmvpdagger2.util.PicassoHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shehabsalah on 2/3/18.
 * This module is provide the PicassoHandler instance to be injected from the Activities and
 * fragments.
 */

@Module
public abstract class PicassoModule {

    @Provides
    static PicassoHandler providePicassoHandler(Application context) {
        return new PicassoHandler(context);
    }
}
