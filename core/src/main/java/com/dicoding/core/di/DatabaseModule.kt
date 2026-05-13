package com.dicoding.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.core.data.source.local.room.AnimaxDatabase
import com.dicoding.core.data.source.local.room.AnimeFavoriteDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AnimaxDatabase {
        return Room.databaseBuilder(
            context,
            AnimaxDatabase::class.java,
            "animax.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteAnimeDao(database: AnimaxDatabase): AnimeFavoriteDAO {
        return database.favoriteAnimeDao()
    }
}