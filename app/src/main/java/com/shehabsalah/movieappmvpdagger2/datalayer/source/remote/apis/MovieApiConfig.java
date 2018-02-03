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
package com.shehabsalah.movieappmvpdagger2.datalayer.source.remote.apis;

import com.shehabsalah.movieappmvpdagger2.models.response.MoviesResponse;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ShehabSalah on 1/9/18.
 *
 */

public interface MovieApiConfig {

    @GET(Constants.PAGE_POPULAR)
    Call<MoviesResponse> executePopular(@Query(Constants.PARAM_API_KEY) String apiKey);

    @GET(Constants.PAGE_TOP_RATED)
    Call<MoviesResponse> executeTopRated(@Query(Constants.PARAM_API_KEY) String apiKey);
}
