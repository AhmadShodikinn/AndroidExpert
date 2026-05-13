package com.dicoding.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "anime_favorite")
data class AnimeFavoriteEntity(
    @PrimaryKey
    val animeId: String,
    val title: String,
    val poster: String,
    val episodes: Int,
    val releaseDay: String,
    val releaseDate: String,
    val score: String,
    val href: String,
    val otakudesuUrl: String,
    val type: String
)