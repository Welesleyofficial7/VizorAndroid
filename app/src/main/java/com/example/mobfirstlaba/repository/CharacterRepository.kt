package com.example.mobfirstlaba.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.mobfirstlaba.models.CharacterModel
import com.example.mobfirstlaba.network.ApiClient
import java.io.File

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

    fun saveHeroesToFile(context: Context, heroes: List<String?>, fileName: String): Boolean {
        val externalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        if (!externalStorage.exists()) {
            externalStorage.mkdir()
        }
        val file = File(externalStorage, fileName)
        return try {
            file.writeText(heroes.joinToString("\n"))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
