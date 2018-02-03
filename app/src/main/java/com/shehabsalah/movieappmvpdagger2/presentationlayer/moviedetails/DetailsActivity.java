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

import android.os.Bundle;
import android.view.MenuItem;

import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.util.ActivityUtils;
import com.shehabsalah.movieappmvpdagger2.util.Constants;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class DetailsActivity extends DaggerAppCompatActivity {

    DetailsFragment detailsFragment;

    @Inject
    Lazy<DetailsFragment> detailsFragmentProvider;
    @Inject
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set an exit transition
        getWindow().setAllowEnterTransitionOverlap(true);
        setContentView(R.layout.activity_details);

        setTitle(movie.getTitle());

        //Create the movie Fragment
        detailsFragment =
                (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.details_container);
        if (detailsFragment == null){
            detailsFragment = detailsFragmentProvider.get();
            Bundle args = new Bundle();
            Bundle extras = getIntent().getExtras();
            if (getIntent().hasExtra(Constants.KEY_CONNECTION_IMAGE) && extras != null)
                args.putString(Constants.KEY_CONNECTION_IMAGE, extras.getString(Constants.KEY_CONNECTION_IMAGE));
            detailsFragment.setArguments(args);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), detailsFragment, R.id.details_container);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
