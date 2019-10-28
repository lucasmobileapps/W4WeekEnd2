package com.example.w4weekend.database

import android.database.Cursor
import androidx.room.*

@Dao
interface FavoriteDAO{
    @Query("SELECT * FROM favorites")
    fun getAllPersons(): List<FavoriteEntity>

    @Query("DELETE FROM favorites")
    fun deleteAllPersons(): Int

    @Insert
    fun insertNewFavorite(newFavorite: FavoriteEntity)
    }