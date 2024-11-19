package com.example.mobfirstlaba.repository

import android.util.Log
import com.example.mobfirstlaba.models.CharacterModel
import com.example.mobfirstlaba.network.ApiClient

class CharacterRepository {
    private val apiService = ApiClient.apiService

    suspend fun getCharacters(): List<CharacterModel>? {
        return try {
            val response = apiService.getCharacters()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("CharacterRepository", "Error: ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Exception: ${e.message}")
            emptyList()
        }
    }
}

