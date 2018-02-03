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
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by ShehabSalah on 1/12/18.
 * Immutable model class for a movie reviews.
 */
@Entity(tableName = "reviews",
        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Movie.class, parentColumns = "movie_id", childColumns = "movie_id"),
        indices = {@Index(value = {"review_id"}, unique = true), @Index(value = {"movie_id"})})
public class MovieReviews {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_db_id")
    private int dbId;

    @SerializedName("id")
    @ColumnInfo(name = "review_id")
    private String reviewId;

    @SerializedName("author")
    @ColumnInfo(name = "author")
    private String author;

    @SerializedName("content")
    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    public MovieReviews(int dbId, String reviewId, String author, String content, int movieId) {
        this.dbId = dbId;
        this.reviewId = reviewId;
        this.author = author;
        this.content = content;
        this.movieId = movieId;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
