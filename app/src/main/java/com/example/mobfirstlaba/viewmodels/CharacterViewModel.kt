package com.example.mobfirstlaba.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mobfirstlaba.repository.CharacterRepository
import androidx.lifecycle.liveData

class CharacterViewModel(context: Context) : ViewModel() {
    private val repository = CharacterRepository(context)

    val characters = liveData {
        val data = repository.getCharactersFromDb()
        emit(data)
    }

    suspend fun fetchAndSaveCharacters() {
        val apiCharacters = repository.getCharacters()
        apiCharacters?.let { repository.saveCharactersToDb(it) }
    }
}
