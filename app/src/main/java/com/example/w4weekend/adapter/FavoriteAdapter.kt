package com.example.w4weekend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.w4weekend.R
import com.example.w4weekend.database.FavoriteEntity
import kotlinx.android.synthetic.main.favorite_item_view_layout.view.*

class FavoriteAdapter(private val favoriteAdapterDelegate: FavoriteAdapterDelegate)
    : ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffUtil()){

    interface FavoriteAdapterDelegate{
        fun favoriteSelect(favorite: FavoriteEntity)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_view_layout, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.apply {
            favoriteName.text = getItem(position).favoriteName
            favoriteCoord.text = "${getItem(position).favoriteLatitude}, ${getItem(position).favoriteLongitude}"
            viewGroup.setOnClickListener{
                favoriteAdapterDelegate.favoriteSelect(getItem(position))
            }
        }
    }

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val favoriteName : TextView = view.findViewById(R.id.favoriteName_textiew)
        val favoriteCoord : TextView = view.findViewById(R.id.favoriteCoord_textiew)
        val viewGroup: ConstraintLayout = view.findViewById(R.id.favorite_itemview)

    }
}

class FavoriteDiffUtil: DiffUtil.ItemCallback<FavoriteEntity>(){
    override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
        return oldItem.favoriteLongitude == newItem.favoriteLongitude && oldItem.favoriteLatitude == newItem.favoriteLatitude
    }
}