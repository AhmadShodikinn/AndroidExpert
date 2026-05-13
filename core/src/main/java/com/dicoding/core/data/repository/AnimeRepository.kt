package com.dicoding.core.data.repository

import com.dicoding.core.data.mapper.DataMapper
import com.dicoding.core.data.mapper.DataMapper.toAnimeList
import com.dicoding.core.data.mapper.DataMapper.toFavoriteEntity
import com.dicoding.core.data.mapper.DataMapper.toScheduleAnime
import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.domain.model.Anime
import com.dicoding.core.domain.model.AnimeDetail
import com.dicoding.core.domain.model.ScheduleAnime
import com.dicoding.core.domain.repository.IAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AnimeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAnimeRepository {

    override fun getHomeAnime(): Flow<Result<List<Anime>>> {
        return remoteDataSource.getHome()
            .map { response ->
                val ongoingAnime = response.data?.ongoing?.animeList
                    .orEmpty()
                    .filterNotNull()
                    .map { it to "Ongoing" }

                val completedAnime = response.data?.completed?.animeList
                    .orEmpty()
                    .filterNotNull()
                    .map { it to "Completed" }

                val animeList = DataMapper.mapAnimeResponsesToDomain(
                    ongoingAnime + completedAnime
                )

                Result.success(animeList)
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override fun getDetailAnime(animeId: String): Flow<Result<AnimeDetail>> {
        return remoteDataSource.getDetailAnime(animeId)
            .map { response ->
                val detailAnime = DataMapper.mapDetailResponseToDomain(response)
                Result.success(detailAnime)
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override fun getScheduleAnime(): Flow<Result<List<ScheduleAnime>>> {
        return remoteDataSource.getScheduleAnime()
            .map { response ->
                val scheduleAnime = response.data
                    ?.filterNotNull()
                    ?.map { it.toScheduleAnime() }
                    .orEmpty()

                Result.success(scheduleAnime)
            }
            .catch { e ->
                emit(Result.failure(e))
            }
    }

    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return localDataSource.getFavoriteAnime()
            .map { it.toAnimeList() }
    }

    override fun isFavoriteAnime(animeId: String): Flow<Boolean> {
        return localDataSource.isFavoriteAnime(animeId)
    }

    override suspend fun insertFavoriteAnime(anime: Anime) {
        localDataSource.insertFavoriteAnime(anime.toFavoriteEntity())
    }

    override suspend fun deleteFavoriteAnime(animeId: String) {
        localDataSource.deleteFavoriteAnime(animeId)
    }
}