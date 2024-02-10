package com.surajpal.a1_dictionary.api

import com.surajpal.a1_dictionary.repository.DictionaryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitInstance.kt
object RetrofitInstance {

    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi: DictionaryApi by lazy {
        retrofit.create(DictionaryApi::class.java)
    }
}
