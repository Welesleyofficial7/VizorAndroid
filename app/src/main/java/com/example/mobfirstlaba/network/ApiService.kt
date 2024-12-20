package com.example.mobfirstlaba.network

import com.example.mobfirstlaba.models.CharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 50
    ): Response<List<CharacterModel>>

}

