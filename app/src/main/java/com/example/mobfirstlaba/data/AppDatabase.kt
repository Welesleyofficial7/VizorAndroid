package com.example.mobfirstlaba.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobfirstlaba.daos.CharacterDao
import com.example.mobfirstlaba.models.CharacterEntity
import com.example.mobfirstlaba.utils.Converters

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
