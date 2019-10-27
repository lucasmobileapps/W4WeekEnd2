package com.example.w4weekend.database

import android.database.Cursor
import androidx.room.*

@Dao
interface FavoriteDAO{
    @Query("SELECT * FROM favorites")
    fun getAllPersons(): MutableList<FavoriteEntity>

    @Query("SELECT * FROM favorites")
    fun getAllPersonstoProvider(): Cursor

    @Query("DELETE FROM favorites")
    fun deleteAllPersons(): Int

    @Delete
    fun deletePerson(favoriteEntity: FavoriteEntity)

    @Insert
    fun insertNewFavorite(newFavorite: FavoriteEntity)


    @Update
    fun updatePerson(favoriteEntity: FavoriteEntity)

    @Insert
    fun insertAllPersons(vararg favoriteEntity: FavoriteEntity)
}