package com.example.dicodingevent.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEvent(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String? = "",
    val mediaCover: String? = null,
)