package com.dicoding.core.domain.model

data class Anime(
    val animeId: String,
    val title: String,
    val poster: String,
    val episodes: Int,
    val releaseDay: String,
    val releaseDate: String,
    val score: String,
    val href: String,
    val otakudesuUrl: String,
    val type: String,
    val isFavorite: Boolean = false
)