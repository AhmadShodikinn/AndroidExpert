package com.dicoding.animax.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideMessage(): String {
        return "Test dagger"
    }
}