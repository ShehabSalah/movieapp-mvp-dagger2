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

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.di.scopes.ActivityScope;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.util.MessageHandler;
import com.shehabsalah.movieappmvpdagger2.util.PicassoHandler;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * Created by ShehabSalah on 1/8/18.
 * Display a grid of {@link Movie}s. User can choose to View all most popular, top rated, or favorite movies.
 */

@ActivityScope
public class MoviesListFragment extends DaggerFragment implements MoviesContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeToRefresh;
    @BindView(R.id.message_container)
    RelativeLayout messageContainer;
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindView(R.id.layout_container)
    BlurView blurView;
    @Inject
    MoviesContract.Presenter mPresenter;
    @Inject
    PicassoHandler picassoHandler;
    private MoviesListAdapter adapter;
    private boolean savedState = false;


    @Inject
    public MoviesListFragment() {
        // Requires empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.movies_list, container, false);
        ButterKnife.bind(this, mainView);
        mPresenter.setActivity(getActivity());
        adapter = new MoviesListAdapter(new ArrayList<>(0), mPresenter, picassoHandler);
        initViews();
        GridLayoutManager gridLayoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        else
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);

        //noinspection ConstantConditions
        swipeToRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent)
        );
        swipeToRefresh.setOnRefreshListener(() -> {
            swipeToRefresh.setRefreshing(true);
            mPresenter.setBasicInit(true, true);
            mPresenter.loadMovies();
        });
        if (savedInstanceState == null) {
            swipeToRefresh.setRefreshing(true);
            mPresenter.setAdvancedInit(MoviesFilter.MOST_POPULAR, true, true);
        }
        return mainView;
    }

    private void initViews() {
        float radius = 10;
        //noinspection ConstantConditions
        View decorView = getActivity().getWindow().getDecorView();
        //Activity's root View. Can also be root View of your layout (preferably)
        ViewGroup rootView = decorView.findViewById(android.R.id.content);
        //set background, if your root layout doesn't have one
        Drawable windowBackground = decorView.getBackground();
        blurView.setVisibility(View.GONE);
        blurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(getActivity()))
                .blurRadius(radius);
    }

    private void showMessageError() {
        messageContainer.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(ArrayList<Movie> movies, boolean setAdapter) {
        if (swipeToRefresh != null)
            swipeToRefresh.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
        messageContainer.setVisibility(View.GONE);
        adapter.replaceData(movies);
        if (setAdapter)
            recyclerView.setAdapter(adapter);
    }

    @Override
    public void showNoMovies() {
        if (swipeToRefresh != null)
            swipeToRefresh.setRefreshing(false);
        errorMessage.setText(R.string.no_movies);
        showMessageError();
    }

    @Override
    public void showNoFavorites() {
        if (swipeToRefresh != null)
            swipeToRefresh.setRefreshing(false);
        errorMessage.setText(R.string.no_favorite);
        showMessageError();
    }

    @Override
    public void showServerError(String error) {
        if (swipeToRefresh != null)
            swipeToRefresh.setRefreshing(false);
        MessageHandler.alertDialog(getActivity(), error, null);
    }

    @Override
    public void showNoInternetConnection() {
        if (swipeToRefresh != null)
            swipeToRefresh.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.no_internet_error_message, Toast.LENGTH_LONG).show();
    }

    public void setType(MoviesFilter moviesSortType, boolean setAdapter, boolean reload) {
        mPresenter.setAdvancedInit(moviesSortType, setAdapter, false);
        if (reload)
            mPresenter.loadMovies();
    }

    public MoviesFilter getType() {
        return mPresenter.getMoviesType();
    }

    @Override
    public void makeBackgroundBlur() {
        //noinspection ConstantConditions
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null)
            vibrator.vibrate(50);
        blurView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        savedState = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        if (blurView.getVisibility() == View.VISIBLE || savedState) {
            savedState = false;
            blurView.setVisibility(View.GONE);
            mPresenter.setBasicInit(false, false);
        }
        mPresenter.loadMovies();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();  //prevent leaking activity in
        // case presenter is orchestrating a long running task
    }
}
