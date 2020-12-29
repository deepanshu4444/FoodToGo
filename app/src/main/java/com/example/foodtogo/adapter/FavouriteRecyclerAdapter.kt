package com.example.foodtogo.adapter

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtogo.FoodEntity
import com.example.foodtogo.R
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter (val context : Context , val foodList : List<FoodEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {

    class FavouriteViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val textFoodName: TextView =view.findViewById(R.id.txtFoodName)
        val textFoodPrice: TextView =view.findViewById(R.id.txtFoodPrice)
        val textFoodRating: TextView =view.findViewById(R.id.txtFoodRating)
        val textFoodImage: ImageView =view.findViewById(R.id.imgFoodImage)
        val addToFavButton : Button = view.findViewById(R.id.btnFoodFav)
        val listcontent: LinearLayout =view.findViewById(R.id.listcontent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single,parent,false)

        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size

    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val food = foodList[position]
        holder.textFoodName.text=food.foodName
        holder.textFoodPrice.text= food.foodPrice.toString()
        holder.textFoodRating.text=food.foodRating

        Picasso.get().load(food.foodImage).error(R.drawable.ic_launcher_background).into(holder.textFoodImage)

    }
}