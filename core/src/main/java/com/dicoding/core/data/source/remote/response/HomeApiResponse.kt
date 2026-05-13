package com.dicoding.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HomeApiResponse(

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("pagination")
	val pagination: Any? = null,

	@field:SerializedName("data")
	val data: HomeData? = null,

	@field:SerializedName ("message")
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

data class Ongoing(

	@field:SerializedName("animeList")
	val animeList: List<AnimeListItem?>? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class AnimeListItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("releaseDay")
	val releaseDay: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String? = null,

	@field:SerializedName("episodes")
	val episodes: Int? = null,

	@field:SerializedName("latestReleaseDate")
	val latestReleaseDate: String? = null,

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("lastReleaseDate")
	val lastReleaseDate: String? = null
)

data class Completed(

	@field:SerializedName("animeList")
	val animeList: List<AnimeListItem?>? = null,

	@field:SerializedName("otakudesuUrl")
	val otakudesuUrl: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)

data class HomeData(

	@field:SerializedName("ongoing")
	val ongoing: Ongoing? = null,

	@field:SerializedName("completed")
	val completed: Completed? = null
)
