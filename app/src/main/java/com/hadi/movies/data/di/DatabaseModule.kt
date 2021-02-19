package com.hadi.movies.data.di

import android.content.Context
import androidx.room.Room
import com.hadi.movies.data.db.manager.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigration().build()
}