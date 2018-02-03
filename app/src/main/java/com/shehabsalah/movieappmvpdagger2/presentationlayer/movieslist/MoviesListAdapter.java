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

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.util.Constants;
import com.shehabsalah.movieappmvpdagger2.util.PicassoHandler;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by ShehabSalah on 1/8/18.
 *
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder>{
    private ArrayList<Movie> movies;
    private MoviesContract.Presenter presenter;
    private PicassoHandler picassoHandler;

    MoviesListAdapter(ArrayList<Movie> movies, MoviesContract.Presenter presenter, PicassoHandler picassoHandler) {
        this.movies = movies;
        this.presenter = presenter;
        this.picassoHandler = picassoHandler;
    }

    void replaceData(ArrayList<Movie> movies) {
        setList(movies);
        notifyDataSetChanged();
    }

    private void setList(ArrayList<Movie> movies) {
        if (movies!=null)
            this.movies = movies;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.item_container)
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_container)
        void onItemClicked() {
            presenter.goToDetailsActivity(movies.get(getAdapterPosition()), moviePoster, movieTitle);
        }

        @OnLongClick(R.id.item_container)
        boolean onItemLongClick(){
            presenter.openMoviePreview(movies.get(getAdapterPosition()), moviePoster, movieTitle, cardView);
            return true;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        picassoHandler.getPicasso()
                .load(Constants.IMAGE_URL + movie.getPosterPath())
                .placeholder(R.drawable.placeholder_background)
                .error(R.drawable.placeholder_background)
                .into(holder.moviePoster);
        holder.movieTitle.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies!=null?movies.size():0;
    }
}
