package com.example.dicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.database.AppDatabase
import com.example.dicodingevent.database.FavoriteEvent
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteDao = AppDatabase.getDatabase(application).favoriteEventDao()

    // Semua data favorit
    val allFavorites: LiveData<List<FavoriteEvent>> = favoriteDao.getAllFavorite()

    // Tambah ke favorit
    fun insert (event: FavoriteEvent) {
        viewModelScope.launch {
            favoriteDao.insert(event)
        }
    }

    // Hapus dari favorit
    fun delete (event: FavoriteEvent) {
        viewModelScope.launch {
            favoriteDao.delete(event)
        }
    }

    // Observasi apakah event tertentu adalah favorit
    fun getFavoriteEventById(eventId: String): LiveData<FavoriteEvent> {
        return favoriteDao.getFavoriteEventById(eventId)
    }
}

