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

    fun saveHeroesToFile(context: Context, characters: List<CharacterModel>, fileName: String): Boolean {
        val externalStorage = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (externalStorage == null || !externalStorage.exists()) {
            externalStorage?.mkdir()
        }
        val file = File(externalStorage, fileName)
        val heroNames = characters.mapNotNull { it.name }
        return try {
            file.writeText(heroNames.joinToString("\n"))
            Log.d("CharacterRepository", "File $fileName successfully saved.")
            true
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Failed to save file $fileName: ${e.message}")
            false
        }
    }

    suspend fun saveCharactersToDb(context: Context, characters: List<CharacterModel>, fileName: String) {
        if (characters.isNotEmpty()) {
            clearDatabase(context, fileName)
            val entities = characters.map {
                CharacterEntity(
                    name = it.name,
                    culture = it.culture ?: "Unknown",
                    born = it.born ?: "Unknown",
                    titles = it.titles.joinToString(","),
                    aliases = it.aliases.joinToString(","),
                    playedBy = it.playedBy.joinToString(",")
                )
            }
            characterDao.insertCharacters(entities)
            saveHeroesToFile(context, characters, fileName)
        }
    }


    suspend fun clearDatabase(context: Context, fileName: String) {
        characterDao.clearCharacters()
        saveHeroesToFile(context, emptyList(), fileName)
    }


    suspend fun getCharactersByPage(page: Int, context: Context): List<CharacterModel>? {
        return try {
            val response = apiService.getCharacters(page)
            if (response.isSuccessful) {
                val characters = response.body()
                if (!characters.isNullOrEmpty()) {
                    saveCharactersToDb(context, characters, "heroes_1.txt")
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

    fun observeCharacters(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }

}
