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
 * Immutable model class for a movie trailers.
 */

@Entity(tableName = "trailers",
        foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Movie.class, parentColumns = "movie_id", childColumns = "movie_id"),
        indices = {@Index(value = {"trailer_id"}, unique = true) , @Index(value = {"movie_id"})})
public class MovieTrailers {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trailer_db_id")
    private int dbId;

    @SerializedName("id")
    @ColumnInfo(name = "trailer_id")
    private String trailerId;

    @SerializedName("key")
    @ColumnInfo(name = "key")
    private String key;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("size")
    @ColumnInfo(name = "size")
    private int size;

    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    public MovieTrailers(int dbId, String trailerId, String key, String name, int size, String type, int movieId) {
        this.dbId = dbId;
        this.trailerId = trailerId;
        this.key = key;
        this.name = name;
        this.size = size;
        this.type = type;
        this.movieId = movieId;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
