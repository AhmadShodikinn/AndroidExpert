package com.dicoding.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.AnimeUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModel() {

    fun getFavoriteAnime() = animeUseCase.getFavoriteAnime().asLiveData()
}