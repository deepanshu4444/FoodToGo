package com.example.foodtogo.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodtogo.FoodDatabase
import com.example.foodtogo.FoodEntity
import com.example.foodtogo.R
import com.example.foodtogo.model.Food
import com.squareup.picasso.Picasso



class HomeRecyclerAdapter (val context: Context, val itemlist : ArrayList<Food>):
    RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val textFoodName: TextView =view.findViewById(R.id.txtFoodName)
        val textFoodPrice: TextView =view.findViewById(R.id.txtFoodPrice)
        val textFoodRating: TextView =view.findViewById(R.id.txtFoodRating)
        val textFoodImage: ImageView =view.findViewById(R.id.imgFoodImage)
        val addToFavButton :Button = view.findViewById(R.id.btnFoodFav)
        val listcontent: LinearLayout =view.findViewById(R.id.listcontent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_home_single,parent,false)

        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {

        return itemlist.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val food = itemlist[position]
        holder.textFoodName.text=food.foodName
        holder.textFoodPrice.text= food.foodPrice.toString()
        holder.textFoodRating.text=food.foodRating



        Picasso.get().load(food.foodImage).error(R.drawable.ic_launcher_background).into(holder.textFoodImage)


        val foodEntity = FoodEntity(
            food.foodId,
            food.foodName,
            food.foodRating,
            food.foodPrice.toString(),
            food.foodImage

        )

        val checkFav = DBAsyncTask(context,foodEntity,1).execute()
        val isFav = checkFav.get()

        if(isFav)
        {
            holder.addToFavButton.setBackgroundResource(R.drawable.ic_favorite_24dp)
        }else
        {
            holder.addToFavButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
        }

        holder.addToFavButton.setOnClickListener {

            if(!DBAsyncTask(context,foodEntity,1).execute().get())
            {
                val async = DBAsyncTask(context,foodEntity,2).execute()
                val result = async.get()
                if(result)
                {
                    Toast.makeText(context,"Restaurant Added to Favourites",Toast.LENGTH_LONG).show()
                    holder.addToFavButton.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp)
                }
                else
                {
                    Toast.makeText(context,"Some Error Occured",Toast.LENGTH_LONG).show()
                }
            }else
            {
                val async = DBAsyncTask(context,foodEntity,3).execute()
                val result = async.get()
                if(result)
                {
                    Toast.makeText(context,"Restaurant Removed From Favourite",Toast.LENGTH_LONG).show()
                    holder.addToFavButton.setBackgroundResource(R.drawable.ic_favorite_24dp)
                }
                else
                {
                    Toast.makeText(context,"Some Error Occured",Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    class DBAsyncTask(val context: Context, private val foodEntity: FoodEntity, private val mode :Int): AsyncTask<Void, Void, Boolean>() {

        /*
        mode 1 : to check whether a book is favourite or not
        mode 2 : to save the database
        mode 3 :to delete from the favourite
        */

        val db = Room.databaseBuilder(context, FoodDatabase::class.java,"food-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {

            when(mode)
            {
                1->
                {

                    val  book : FoodEntity?=db.foodDoa().getFoodById(foodEntity.id.toString())
                    db.close()
                    return book!=null
                }

                2->{

                    db.foodDoa().insertFood(foodEntity)
                    db.close()
                    return true
                }

                3->{

                    db.foodDoa().deleteFood(foodEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }
}
