package com.example.w4weekend.presenter

import com.example.w4weekend.database.FavoriteEntity

interface PlacesView {
    fun displayPlaces(list: List<FavoriteEntity>)
}
interface Presenter {
    fun addPlace(favoriteEntity: FavoriteEntity)
}