package com.dicoding.core.domain.repository

import com.dicoding.core.domain.model.Anime
import com.dicoding.core.domain.model.AnimeDetail
import com.dicoding.core.domain.model.ScheduleAnime
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {

    fun getHomeAnime(): Flow<Result<List<Anime>>>

    fun getDetailAnime(animeId: String): Flow<Result<AnimeDetail>>

    fun getScheduleAnime(): Flow<Result<List<ScheduleAnime>>>

    // local room db
    fun getFavoriteAnime(): Flow<List<Anime>>

    fun isFavoriteAnime(animeId: String): Flow<Boolean>

    suspend fun insertFavoriteAnime(anime: Anime)

    suspend fun deleteFavoriteAnime(animeId: String)
}