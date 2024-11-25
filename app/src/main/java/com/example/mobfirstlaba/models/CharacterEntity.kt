package com.example.mobfirstlaba.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val culture: String?,
    val born: String?,
    val titles: String?, // Убедитесь, что это String?
    val aliases: String?, // Убедитесь, что это String?
    val playedBy: String? // Убедитесь, что это String?
)

