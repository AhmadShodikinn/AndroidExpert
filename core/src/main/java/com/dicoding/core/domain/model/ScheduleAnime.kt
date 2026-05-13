package com.dicoding.core.domain.model

data class ScheduleAnime(
    val day: String,
    val animeList: List<ScheduleAnimeItem>
)