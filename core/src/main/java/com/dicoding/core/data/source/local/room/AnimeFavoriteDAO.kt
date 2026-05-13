package com.dicoding.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.core.data.source.local.entity.AnimeFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeFavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteAnime(anime: AnimeFavoriteEntity)

    @Query("DELETE FROM anime_favorite WHERE animeId = :animeId")
    suspend fun deleteFavoriteAnime(animeId: String)

    @Query("SELECT * FROM anime_favorite ORDER BY title ASC")
    fun getFavoriteAnime(): Flow<List<AnimeFavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM anime_favorite WHERE animeId = :animeId)")
    fun isFavoriteAnime(animeId: String): Flow<Boolean>
}