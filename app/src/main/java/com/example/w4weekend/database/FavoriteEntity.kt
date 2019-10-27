package com.example.w4weekend.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "favorite_latitude") var favoriteLatitude: Double,
    @ColumnInfo(name = "favorite_longitude") var favoriteLongitude: Double,
    @ColumnInfo(name = "favorite_name") var favoriteName: String)


{
    constructor(
        favoriteLatitude: Double, favoriteLongitude: Double, favoriteName: String
    ) : this(null, favoriteLatitude, favoriteLongitude, favoriteName)
}