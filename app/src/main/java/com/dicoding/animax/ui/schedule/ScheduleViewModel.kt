package com.dicoding.animax.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.AnimeUseCase
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    animeUseCase: AnimeUseCase
) : ViewModel() {

    val scheduleAnime = animeUseCase.getScheduleAnime().asLiveData()
}