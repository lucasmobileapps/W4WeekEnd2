package com.example.w4weekend.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 2)
abstract class FavoriteDatabase: RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDAO
}