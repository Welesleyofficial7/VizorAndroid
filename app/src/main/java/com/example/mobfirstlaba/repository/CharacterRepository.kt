package com.example.mobfirstlaba.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.room.Room
import com.example.mobfirstlaba.data.AppDatabase
import com.example.mobfirstlaba.models.CharacterEntity
import com.example.mobfirstlaba.models.CharacterModel
import com.example.mobfirstlaba.network.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import java.io.File

class CharacterRepository(context: Context) {
    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "character_database"
    ).build()

    private val characterDao = database.characterDao()
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

    suspend fun saveCharactersToDb(characters: List<CharacterModel>) {
        if (characters.isNotEmpty()) {
            clearDatabase()
            val entities = characters.map {
                CharacterEntity(
                    name = it.name,
                    culture = it.culture ?: "Unknown", // Provide a default value
                    born = it.born ?: "Unknown", // Handle null safely
                    titles = it.titles?.joinToString(",") ?: "", // Handle null safely
                    aliases = it.aliases?.joinToString(",") ?: "", // Handle null safely
                    playedBy = it.playedBy?.joinToString(",") ?: "" // Handle null safely
                )
            }
            characterDao.insertCharacters(entities)
        }
    }


    suspend fun getCharactersByPage(page: Int): List<CharacterModel>? {
        return try {
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val characters = response.body()
                if (!characters.isNullOrEmpty()) {
                    saveCharactersToDb(characters)
                }
                characters
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Exception: ${e.message}")
            emptyList()
        }
    }


    suspend fun getCharactersFromDb(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

    suspend fun clearDatabase() {
        characterDao.clearCharacters()
    }

    fun observeCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

}
