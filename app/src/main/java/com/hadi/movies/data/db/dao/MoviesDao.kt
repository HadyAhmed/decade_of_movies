package com.hadi.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hadi.movies.data.model.local.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("DELETE FROM Movie")
    suspend fun clearDatabase()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(vararg movie: Movie)

    @Query("SELECT * FROM Movie WHERE (title LIKE :movieTitle OR :movieTitle==NULL) ORDER BY year DESC LIMIT 5")
    fun searchForMovies(movieTitle: String? = null): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id=:movieId")
    fun fetchMovieDetails(movieId: Int): Flow<Movie>
}