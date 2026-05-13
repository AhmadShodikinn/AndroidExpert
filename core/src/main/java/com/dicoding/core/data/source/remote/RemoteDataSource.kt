package com.dicoding.core.data.source.remote

import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.data.source.remote.response.DetailApiResponse
import com.dicoding.core.data.source.remote.response.HomeApiResponse
import com.dicoding.core.data.source.remote.response.ScheduleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService
) {

    fun getHome(): Flow<HomeApiResponse> {
        return flow {
            emit(apiService.getHome())
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailAnime(animeId: String): Flow<DetailApiResponse> {
        return flow {
            emit(apiService.getDetailAnime(animeId))
        }.flowOn(Dispatchers.IO)
    }

    fun getScheduleAnime(): Flow<ScheduleApiResponse> {
        return flow {
            emit(apiService.getScheduleAnime())
        }.flowOn(Dispatchers.IO)
    }
}