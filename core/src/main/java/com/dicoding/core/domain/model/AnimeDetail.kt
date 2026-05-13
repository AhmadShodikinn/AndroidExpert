package com.dicoding.core.domain.model

data class AnimeDetail(
    val title: String,
    val japanese: String,
    val poster: String,
    val score: String,
    val status: String,
    val type: String,
    val episodes: String,
    val duration: String,
    val aired: String,
    val studios: String,
    val producers: String,
    val genres: List<String>,
    val synopsis: String,
    val episodeList: List<Episode>
)