package com.example.dicodingevent.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEvent: FavoriteEvent)

    @Delete
    suspend fun delete(favoriteEvent: FavoriteEvent)

    @Query("SELECT * FROM favorite_events ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM favorite_events WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent>
    }