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

package com.shehabsalah.movieappmvpdagger2.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ShehabSalah on 1/8/18.
 * Immutable model class for a Movie.
 */
@Entity(tableName = "movies", indices = {@Index(value = {"movie_id"}, unique = true)})
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "db_id")
    private int dbId;

    @SerializedName("id")
    @ColumnInfo(name = "movie_id")
    private int movieId;

    @Nullable
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @Nullable
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;

    @Nullable
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @Nullable
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private String originalTitle;

    @Nullable
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @Nullable
    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @Nullable
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    private float popularity;

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private float voteAverage;

    @ColumnInfo(name = "favorite")
    private int favorite;

    @ColumnInfo(name = "movie_type")
    private String type;


    public Movie(int dbId, int movieId, @Nullable String posterPath, @Nullable String overview, @Nullable String releaseDate,
                 @Nullable String originalTitle, @Nullable String originalLanguage, @Nullable String title,
                 @Nullable String backdropPath, float popularity, int voteCount,
                 float voteAverage, int favorite, @Nullable String type) {
        this.dbId = dbId;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.favorite = favorite;
        this.type = type;
    }

    Movie(Parcel in){
        String[] data = new String[14];
        in.readStringArray(data);
        this.dbId                   = Integer.parseInt(data[0]);
        this.movieId                = Integer.parseInt(data[1]);
        this.posterPath             = data[2];
        this.overview               = data[3];
        this.releaseDate            = data[4];
        this.originalTitle          = data[5];
        this.originalLanguage       = data[6];
        this.title                  = data[7];
        this.backdropPath           = data[8];
        this.popularity             = Float.parseFloat(data[9]);
        this.voteCount              = Integer.parseInt(data[10]);
        this.voteAverage            = Float.parseFloat(data[11]);
        this.favorite               = Integer.parseInt(data[12]);
        this.type                   = data[13];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                String.valueOf(this.dbId),
                String.valueOf(this.movieId),
                this.posterPath,
                this.overview,
                this.releaseDate,
                this.originalTitle,
                this.originalLanguage,
                this.title,
                this.backdropPath,
                String.valueOf(this.popularity),
                String.valueOf(voteCount),
                String.valueOf(voteAverage),
                String.valueOf(favorite),
                this.type
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Nullable
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(@Nullable String posterPath) {
        this.posterPath = posterPath;
    }

    @Nullable
    public String getOverview() {
        return overview;
    }

    public void setOverview(@Nullable String overview) {
        this.overview = overview;
    }

    @Nullable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@Nullable String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Nullable
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(@Nullable String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Nullable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(@Nullable String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(@Nullable String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
