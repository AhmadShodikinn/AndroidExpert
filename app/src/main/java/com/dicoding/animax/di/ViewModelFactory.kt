package com.dicoding.animax.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.animax.ui.detail.DetailViewModel
import com.dicoding.animax.ui.home.HomeViewModel
import com.dicoding.animax.ui.schedule.ScheduleViewModel
import com.dicoding.core.domain.usecase.AnimeUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val animeUseCase: AnimeUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(animeUseCase) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(animeUseCase) as T
            }

            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                ScheduleViewModel(animeUseCase) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}