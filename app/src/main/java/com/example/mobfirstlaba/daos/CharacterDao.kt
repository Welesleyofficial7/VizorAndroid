package com.example.mobfirstlaba.daos

import androidx.room.*
import com.example.mobfirstlaba.models.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>): List<Long>

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters(): Int
}


