package com.dicoding.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.core.data.source.local.entity.AnimeFavoriteEntity

@Database(
    entities = [AnimeFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AnimaxDatabase : RoomDatabase() {

    abstract fun favoriteAnimeDao(): AnimeFavoriteDAO
}