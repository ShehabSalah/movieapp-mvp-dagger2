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
package com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.models.MovieReviews;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ShehabSalah on 1/12/18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private ArrayList<MovieReviews> movieReviews;

    public ReviewsAdapter(ArrayList<MovieReviews> movieReviews) {
        this.movieReviews = movieReviews;
    }

    void replaceData(ArrayList<MovieReviews> movieReviews) {
        setList(movieReviews);
        notifyDataSetChanged();
    }

    private void setList(ArrayList<MovieReviews> movieReviews) {
        if (movieReviews != null)
            this.movieReviews = movieReviews;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView reviewAuthor;
        @BindView(R.id.review_content)
        TextView reviewContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_reviews, parent, false);

        return new ReviewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieReviews mReview = movieReviews.get(position);
        holder.reviewAuthor.setText(mReview.getAuthor());
        holder.reviewContent.setText(mReview.getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviews != null ? movieReviews.size() : 0;
    }
}
