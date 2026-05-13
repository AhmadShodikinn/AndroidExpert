package com.dicoding.animax.di


import android.content.Context
import com.dicoding.animax.core.di.NetworkModule
import com.dicoding.animax.core.di.RepositoryModule
import com.dicoding.animax.ui.detail.DetailAnimeActivity
import com.dicoding.animax.ui.home.HomeActivity
import com.dicoding.animax.ui.schedule.ScheduleActivity
import com.dicoding.core.di.DatabaseModule
import com.dicoding.core.domain.usecase.AnimeUseCase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
    ]
)
interface AppComponent {
    fun inject(activity: HomeActivity)

    fun inject(activity: DetailAnimeActivity)

    fun inject(activity: ScheduleActivity)

    fun animeUseCase(): AnimeUseCase

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}