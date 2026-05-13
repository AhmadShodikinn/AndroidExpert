package com.dicoding.animax.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.AnimeUseCase
import javax.inject.Inject
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.core.domain.model.Anime
import kotlinx.coroutines.launch

class DetailViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    fun getDetailAnime(animeId: String) =
        animeUseCase.getDetailAnime(animeId).asLiveData()

    // local room-db
    fun isFavoriteAnime(animeId: String) =
        animeUseCase.isFavoriteAnime(animeId).asLiveData()

    fun insertFavoriteAnime(anime: Anime) {
        viewModelScope.launch {
            animeUseCase.insertFavoriteAnime(anime)
        }
    }

    fun deleteFavoriteAnime(animeId: String) {
        viewModelScope.launch {
            animeUseCase.deleteFavoriteAnime(animeId)
        }
    }
}