package com.dicoding.core.utils

import com.dicoding.core.data.source.local.entity.AnimeFavoriteEntity
import com.dicoding.core.domain.model.Anime

fun AnimeFavoriteEntity.toAnime(): Anime {
    return Anime(
        animeId = animeId,
        title = title,
        poster = poster,
        episodes = episodes,
        releaseDay = releaseDay,
        releaseDate = releaseDate,
        score = score,
        href = href,
        otakudesuUrl = otakudesuUrl,
        type = type,
        isFavorite = true
    )
}

fun List<AnimeFavoriteEntity>.toAnimeList(): List<Anime> {
    return map { it.toAnime() }
}