package com.example.w4weekend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.w4weekend.database.FavoriteDatabase
import com.example.w4weekend.database.FavoriteEntity

class FavoriteEntityViewModel(app: Application) : AndroidViewModel (app){
    val favoriteDatabase :FavoriteDatabase by lazy {
        Room.databaseBuilder(
            app.applicationContext,
            FavoriteDatabase::class.java,
            "favorite.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    fun addPlace(favoriteEntity: FavoriteEntity){
        favoriteDatabase.favoriteDao().insertNewFavorite(favoriteEntity)
    }
    fun getPlaces():List<FavoriteEntity>{
        return favoriteDatabase.favoriteDao().getAllPersons()
    }
    fun clearAllPlaces(){
        favoriteDatabase.favoriteDao().deleteAllPersons()
    }
}