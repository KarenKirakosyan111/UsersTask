package com.example.userstask.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://randomuser.me/"

interface UserApiService {

    @GET("api")
    fun getUsersAsync(
        @Query("page") page: Int = 1,
        @Query("seed") seed: String = "renderforest",
        @Query("results") results: Int = 20
    ): Deferred<UserDataResponse>

    companion object {
        operator fun invoke(): UserApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApiService::class.java)
        }
    }
}