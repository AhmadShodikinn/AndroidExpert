package com.dicoding.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScheduleApiResponse(

	@field:SerializedName("creator")
	val creator: String? = null,

	@field:SerializedName("data")
	val data: List<ScheduleDataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ScheduleAnimeListItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class ScheduleDataItem(

	@field:SerializedName("anime_list")
	val animeList: List<ScheduleAnimeListItem?>? = null,

	@field:SerializedName("day")
	val day: String? = null
)
