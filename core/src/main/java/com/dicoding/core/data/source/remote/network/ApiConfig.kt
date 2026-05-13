package com.dicoding.core.data.source.remote.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.dicoding.animax.core.BuildConfig

class ApiConfig {
    companion object {

        private val BASE_URL = BuildConfig.BASE_URL

        fun getApiService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getokHttpClient())
                .build()
                .create(ApiService::class.java)
        }

        private fun getokHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient.Builder().addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()
        }
    }
}