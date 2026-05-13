package com.dicoding.core.data.source.remote.network

import com.dicoding.core.data.source.remote.response.DetailApiResponse
import com.dicoding.core.data.source.remote.response.HomeApiResponse
import com.dicoding.core.data.source.remote.response.ScheduleApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("anime/home")
    suspend fun getHome(): HomeApiResponse

    @GET("anime/anime/{animeId}")
    suspend fun getDetailAnime(
        @Path("animeId") animeId: String
    ): DetailApiResponse

    @GET("anime/schedule")
    suspend fun getScheduleAnime(): ScheduleApiResponse
}