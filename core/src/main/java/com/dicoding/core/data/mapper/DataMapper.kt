package com.dicoding.core.data.mapper

import com.dicoding.core.data.source.remote.response.DetailApiResponse
import com.dicoding.core.data.source.remote.response.ScheduleAnimeListItem
import com.dicoding.core.data.source.remote.response.ScheduleDataItem
import com.dicoding.core.domain.model.Anime
import com.dicoding.core.domain.model.AnimeDetail
import com.dicoding.core.domain.model.Episode
import com.dicoding.core.domain.model.ScheduleAnime
import com.dicoding.core.domain.model.ScheduleAnimeItem
import com.dicoding.core.data.source.local.entity.AnimeFavoriteEntity
import com.dicoding.core.data.source.remote.response.AnimeListItem

object DataMapper {

    fun mapAnimeResponsesToDomain(
        input: List<Pair<AnimeListItem, String>>
    ): List<Anime> {
        return input.map { item ->
            val anime = item.first
            val type = item.second

            Anime(
                animeId = anime.animeId.orEmpty(),
                title = anime.title.orEmpty(),
                poster = anime.poster.orEmpty(),
                episodes = anime.episodes ?: 0,
                releaseDay = anime.releaseDay.orEmpty(),
                releaseDate = anime.latestReleaseDate ?: anime.lastReleaseDate.orEmpty(),
                score = anime.score.orEmpty(),
                href = anime.href.orEmpty(),
                otakudesuUrl = anime.otakudesuUrl.orEmpty(),
                type = type,
                isFavorite = false
            )
        }
    }

    fun mapDetailResponseToDomain(response: DetailApiResponse): AnimeDetail {
        val data = response.data

        return AnimeDetail(
            title = data?.title.orEmpty(),
            japanese = data?.japanese.orEmpty(),
            poster = data?.poster.orEmpty(),
            score = data?.score
                ?.takeIf { it.isNotBlank() }
                ?: "Tidak ada rating",
            status = data?.status.orEmpty(),
            type = data?.type.orEmpty(),
            episodes = data?.episodes?.toString() ?: "-",
            duration = data?.duration.orEmpty(),
            aired = data?.aired.orEmpty(),
            studios = data?.studios.orEmpty(),
            producers = data?.producers.orEmpty(),
            genres = data?.genreList
                ?.mapNotNull { it?.title }
                .orEmpty(),
            synopsis = data?.synopsis
                ?.paragraphs
                ?.mapNotNull { it }
                ?.joinToString("\n\n")
                ?.takeIf { it.isNotBlank() }
                ?: "Tidak ada sinopsis",
            episodeList = data?.episodeList
                ?.mapNotNull { item ->
                    item?.let { episodeItem ->
                        Episode(
                            episodeNumber = episodeItem.eps?.toString().orEmpty(),
                            episodeName = episodeItem.title.orEmpty(),
                            episodeDate = episodeItem.date.orEmpty(),
                            episodeId = episodeItem.episodeId.orEmpty()
                        )
                    }
                }
                .orEmpty()
        )
    }

    private fun ScheduleAnimeListItem.toScheduleAnimeItem(): ScheduleAnimeItem {
        return ScheduleAnimeItem(
            animeId = slug.orEmpty(),
            title = title.orEmpty(),
            poster = poster.orEmpty(),
            href = url.orEmpty()
        )
    }

    fun ScheduleDataItem.toScheduleAnime(): ScheduleAnime {
        return ScheduleAnime(
            day = day.orEmpty(),
            animeList = animeList
                ?.filterNotNull()
                ?.map { it.toScheduleAnimeItem() }
                .orEmpty()
        )
    }

    //  local room-db
    fun Anime.toFavoriteEntity(): AnimeFavoriteEntity {
        return AnimeFavoriteEntity(
            animeId = animeId,
            title = title,
            poster = poster,
            episodes = episodes,
            releaseDay = releaseDay,
            releaseDate = releaseDate,
            score = score,
            href = href,
            otakudesuUrl = otakudesuUrl,
            type = type
        )
    }

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
}