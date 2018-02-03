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

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    MoviesListFragment moviesFragment;

    @Inject
    Lazy<MoviesListFragment> moviesFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the movie Fragment
        moviesFragment =
                (MoviesListFragment) getSupportFragmentManager().findFragmentById(R.id.list_container);
        if (moviesFragment == null) {
            moviesFragment = moviesFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), moviesFragment, R.id.list_container);
        }

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            MoviesFilter currentSortType =
                    (MoviesFilter) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            moviesFragment.setType(currentSortType, true, false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            moviesFragment.setType(MoviesFilter.MOST_POPULAR, true, true);
        } else if (id == R.id.action_top_rated) {
            moviesFragment.setType(MoviesFilter.TOP_RATED, true, true);
        } else if (id == R.id.action_favorite) {
            moviesFragment.setType(MoviesFilter.FAVORITES, true, true);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, moviesFragment.getType());
        super.onSaveInstanceState(outState);
    }

}
