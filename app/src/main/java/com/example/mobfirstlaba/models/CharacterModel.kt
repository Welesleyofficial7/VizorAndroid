package com.example.mobfirstlaba.models
import kotlinx.serialization.Serializable

@Serializable
data class CharacterModel(
    val name: String? = null,
    val culture: String? = null,
    val born: String? = null,
    val titles: List<String> = emptyList(),
    val aliases: List<String> = emptyList(),
    val playedBy: List<String> = emptyList()
)

