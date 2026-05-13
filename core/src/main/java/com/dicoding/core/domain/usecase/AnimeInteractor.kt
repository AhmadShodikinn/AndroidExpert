package com.dicoding.core.domain.usecase

import com.dicoding.core.domain.model.Anime
import com.dicoding.core.domain.model.AnimeDetail
import com.dicoding.core.domain.model.ScheduleAnime
import com.dicoding.core.domain.repository.IAnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimeInteractor @Inject constructor(
    private val animeRepository: IAnimeRepository
) : AnimeUseCase {

    override fun getHomeAnime(): Flow<Result<List<Anime>>> {
        return animeRepository.getHomeAnime()
    }

    override fun getDetailAnime(animeId: String): Flow<Result<AnimeDetail>> {
        return animeRepository.getDetailAnime(animeId)
    }

    override fun getScheduleAnime(): Flow<Result<List<ScheduleAnime>>> {
        return animeRepository.getScheduleAnime()
    }

    // local room-db
    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return animeRepository.getFavoriteAnime()
    }

    override fun isFavoriteAnime(animeId: String): Flow<Boolean> {
        return animeRepository.isFavoriteAnime(animeId)
    }

    override suspend fun insertFavoriteAnime(anime: Anime) {
        animeRepository.insertFavoriteAnime(anime)
    }

    override suspend fun deleteFavoriteAnime(animeId: String) {
        animeRepository.deleteFavoriteAnime(animeId)
    }
}