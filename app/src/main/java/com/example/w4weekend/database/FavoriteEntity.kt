package com.example.w4weekend.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "favorite_latitude") var favoriteLatitude: Double?,
    @ColumnInfo(name = "favorite_longitude") var favoriteLongitude: Double?)


{
    constructor(
        favoriteLatitude: Double?, favoriteLongitude: Double?
    ) : this(null, favoriteLatitude, favoriteLongitude)
}