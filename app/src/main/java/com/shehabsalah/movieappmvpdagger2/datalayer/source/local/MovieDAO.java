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
package com.shehabsalah.movieappmvpdagger2.datalayer.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.shehabsalah.movieappmvpdagger2.models.Movie;
import com.shehabsalah.movieappmvpdagger2.models.MovieReviews;
import com.shehabsalah.movieappmvpdagger2.models.MovieTrailers;

import java.util.List;

/**
 * Created by ShehabSalah on 1/9/18.
 * Data Access Object for the movies table.
 */
@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movies WHERE movie_type = 'popular' ORDER BY popularity DESC")
    List<Movie> selectPopularMovies();

    @Query("SELECT * FROM movies WHERE movie_type = 'top_rated' ORDER BY vote_average DESC, vote_count DESC")
    List<Movie> selectTopRatedMovies();

    @Query("SELECT * FROM movies WHERE favorite = 1")
    List<Movie> selectFavorites();

    /**
     * Insert a movie in the database. If the movie already exists, ignore it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    /**
     * Update a movie.
     *
     * @param movie movie to be updated
     * @return the number of movies updated. This should always be 1.
     */
    @Update
    int updateMovie(Movie movie);

    /**
     * Delete all movies.
     *
     * @return the number of movies deleted.
     */
    @Query("DELETE FROM movies WHERE favorite = 0")
    int deleteAll();

    @Query("SELECT * FROM reviews WHERE movie_id = :movieId")
    List<MovieReviews> selectReviews(int movieId);

    /**
     * Insert a movie review in the database. If the movie review already exists, ignore it.
     *
     * @param movieReviews movie review to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieReview(MovieReviews movieReviews);

    /**
     * Delete a movie review by movie id.
     *
     * @return the number of movie reviews deleted..
     */
    @Query("DELETE FROM reviews WHERE movie_id = :movieId")
    int deleteAllReviews(int movieId);


    @Query("SELECT * FROM trailers WHERE movie_id = :movieId")
    List<MovieTrailers> selectTrailers(int movieId);

    /**
     * Insert a movie trailer in the database. If the movie trailer already exists, ignore it.
     *
     * @param movieTrailers movie trailer to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovieTrailer(MovieTrailers movieTrailers);

    /**
     * Delete a movie trailer by movie id.
     *
     * @return the number of movie trailers deleted..
     */
    @Query("DELETE FROM trailers WHERE movie_id = :movieId")
    int deleteAllTrailers(int movieId);

}
