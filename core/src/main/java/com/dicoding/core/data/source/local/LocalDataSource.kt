package com.dicoding.core.data.source.local

import com.dicoding.core.data.source.local.entity.AnimeFavoriteEntity
import com.dicoding.core.data.source.local.room.AnimeFavoriteDAO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val favoriteAnimeDao: AnimeFavoriteDAO
) {

    fun getFavoriteAnime() =
        favoriteAnimeDao.getFavoriteAnime()

    fun isFavoriteAnime(animeId: String) =
        favoriteAnimeDao.isFavoriteAnime(animeId)

    suspend fun insertFavoriteAnime(anime: AnimeFavoriteEntity) =
        favoriteAnimeDao.insertFavoriteAnime(anime)

    suspend fun deleteFavoriteAnime(animeId: String) =
        favoriteAnimeDao.deleteFavoriteAnime(animeId)
}