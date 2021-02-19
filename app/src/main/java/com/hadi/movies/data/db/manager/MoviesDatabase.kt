package com.hadi.movies.data.db.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hadi.movies.data.db.dao.MoviesDao
import com.hadi.movies.data.model.local.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}