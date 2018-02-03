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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shehabsalah.movieappmvpdagger2.R;
import com.shehabsalah.movieappmvpdagger2.models.MovieTrailers;
import com.shehabsalah.movieappmvpdagger2.presentationlayer.moviedetails.DetailsContract;
import com.shehabsalah.movieappmvpdagger2.util.Constants;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ShehabSalah on 1/12/18.
 *
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.MyViewHolder>{
    private ArrayList<MovieTrailers> movieTrailers;
    private Context mContext;
    private DetailsContract.presenter mPresenter;

    public TrailersAdapter(ArrayList<MovieTrailers> movieTrailers, Context mContext, DetailsContract.presenter mPresenter) {
        this.movieTrailers = movieTrailers;
        this.mContext = mContext;
        this.mPresenter = mPresenter;
    }

    void replaceData(ArrayList<MovieTrailers> movieTrailers) {
        setList(movieTrailers);
        notifyDataSetChanged();
    }

    private void setList(ArrayList<MovieTrailers> movieTrailers) {
        if (movieTrailers!=null)
            this.movieTrailers = movieTrailers;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.trailer_thumbnail)
        ImageView trailerThumbnail;
        @BindView(R.id.trailer_size)
        TextView trailerSize;
        @BindView(R.id.trailer_type)
        TextView trailerType;
        @BindView(R.id.trailer_name)
        TextView trailerName;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.trailer_container)
        public void onTrailerClicked(){
            mPresenter.onTrailerClicked(movieTrailers.get(getAdapterPosition()).getKey());
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_trailer, parent, false);

        return new TrailersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieTrailers mTrailer = movieTrailers.get(position);

        Picasso.with(mContext)
                .load(Constants.YOUTUBE_BASE_URL + mTrailer.getKey() + Constants.HIGH_RES_YOUTUBE_IMG)
                .placeholder(R.drawable.placeholder_background)
                .error(R.drawable.placeholder_background)
                .into(holder.trailerThumbnail);

        holder.trailerName.setText(mTrailer.getName());
        holder.trailerSize.setText(String.valueOf(mTrailer.getSize()));
        holder.trailerType.setText(mTrailer.getType());
    }

    @Override
    public int getItemCount() {
        return movieTrailers!=null?movieTrailers.size():0;
    }
}
