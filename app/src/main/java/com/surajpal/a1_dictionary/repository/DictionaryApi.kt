package com.surajpal.a1_dictionary.repository

import com.surajpal.a1_dictionary.model.WordResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("en/{word}")
    suspend fun getMeaning(@Path("word") word : String) : Response<List<WordResult>>
}
