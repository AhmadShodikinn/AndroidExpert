package com.dicoding.animax.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.AnimeUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    animeUseCase: AnimeUseCase
) : ViewModel() {

    val anime = animeUseCase.getHomeAnime().asLiveData()
}