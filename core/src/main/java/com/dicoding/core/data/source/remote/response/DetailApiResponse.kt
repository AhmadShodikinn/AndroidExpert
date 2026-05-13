package com.dicoding.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailApiResponse(

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("pagination")
	val pagination: Any? = null,

	@field:SerializedName("data")
	val data: DetailData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("ok")
	val ok: Boolean? = null,

	@field:SerializedName("statusMessage")
	val statusMessage: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class GenreListItem(

	@field:SerializedName("genreId")
	val genreId: String? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class RecommendedAnimeListItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String? = null
)

data class Batch(

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("batchId")
	val batchId: String? = null
)

data class Synopsis(

	@field:SerializedName("paragraphs")
	val paragraphs: List<String?>? = null,

	@field:SerializedName("connections")
	val connections: List<ConnectionsItem?>? = null
)

data class EpisodeListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("eps")
	val eps: Float? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("episodeId")
	val episodeId: String? = null
)

data class ConnectionsItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class DetailData(

	@field:SerializedName("aired")
	val aired: String? = null,

	@field:SerializedName("studios")
	val studios: String? = null,

	@field:SerializedName("batch")
	val batch: Batch? = null,

	@field:SerializedName("genreList")
	val genreList: List<GenreListItem?>? = null,

	@field:SerializedName("synopsis")
	val synopsis: Synopsis? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("producers")
	val producers: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("japanese")
	val japanese: String? = null,

	@field:SerializedName("recommendedAnimeList")
	val recommendedAnimeList: List<RecommendedAnimeListItem?>? = null,

	@field:SerializedName("episodeList")
	val episodeList: List<EpisodeListItem?>? = null,

	@field:SerializedName("poster")
	val poster: String? = null,

	@field:SerializedName("episodes")
	val episodes: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
