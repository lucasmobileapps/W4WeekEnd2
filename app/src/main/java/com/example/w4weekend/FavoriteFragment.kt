package com.example.w4weekend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.w4weekend.adapter.FavoriteAdapter
import com.example.w4weekend.database.FavoriteDAO
import com.example.w4weekend.database.FavoriteDatabase
import com.example.w4weekend.database.FavoriteEntity
import com.example.w4weekend.viewmodel.FavoriteEntityViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(), FavoriteAdapter.FavoriteAdapterDelegate{

    val viewModel: FavoriteEntityViewModel by lazy {
        ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(
            FavoriteEntityViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        (favorite_recyclerview.adapter as FavoriteAdapter).submitList(viewModel.getPlaces())
        clear_favorite_button.setOnClickListener {
            viewModel.clearAllPlaces()
            (favorite_recyclerview.adapter as FavoriteAdapter).submitList(viewModel.getPlaces())

        }

    }

    override fun favoriteSelect(favorite: FavoriteEntity) {

    }

    fun notifyFavorite(updatedList: MutableList<FavoriteEntity>){
    }

    fun setUpRecyclerView(){
        favorite_recyclerview.adapter = FavoriteAdapter(this)
        favorite_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

    }

}