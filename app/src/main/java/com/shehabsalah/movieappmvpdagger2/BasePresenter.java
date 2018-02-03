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
package com.shehabsalah.movieappmvpdagger2;

/**
 * Created by shehabsalah on 2/2/18.
 *
 */

public interface BasePresenter<T> {

    /**
     * Binds presenter with a View when resumed. The Presenter will perform initialization here.
     *
     * @param view the View associated with this presenter
     */
    void takeView(T view);

    /**
     * Drops the reference to the View when destroyed
     */
    void dropView();
}
