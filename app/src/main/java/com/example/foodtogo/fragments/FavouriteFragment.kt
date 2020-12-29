package com.example.foodtogo.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodtogo.FoodDatabase
import com.example.foodtogo.FoodEntity
import com.example.foodtogo.R
import com.example.foodtogo.adapter.FavouriteRecyclerAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavouriteFragment : Fragment() {

    lateinit var recylerFavourite: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var recycleradapter : FavouriteRecyclerAdapter

    var dbFoodList = listOf<FoodEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)

        recylerFavourite = view.findViewById(R.id.recyclerFavourite)
        layoutManager= LinearLayoutManager(activity)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)

        progressLayout.visibility=View.VISIBLE

        dbFoodList= RetrieveFavourites(activity as Context).execute().get()

        if(activity!=null)
        {
            progressLayout.visibility=View.GONE
            recycleradapter= FavouriteRecyclerAdapter(activity as Context,dbFoodList)
            recylerFavourite.adapter=recycleradapter
            recylerFavourite.layoutManager=layoutManager


        }
        return view
    }

    class RetrieveFavourites (val context : Context) : AsyncTask<Void, Void, List<FoodEntity>>() {
        override fun doInBackground(vararg params: Void?): List<FoodEntity> {
            val db = Room.databaseBuilder(context,FoodDatabase::class.java,"food-db").build()

            return  db.foodDoa().getAllFood()
        }
    }

}


