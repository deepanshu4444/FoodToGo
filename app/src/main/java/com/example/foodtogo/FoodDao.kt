package com.example.foodtogo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface FoodDao {

    @Insert
    fun insertFood(foodEntity: FoodEntity)

    @Delete
    fun deleteFood(foodEntity: FoodEntity)

    @Query("SELECT * FROM food")
    fun getAllFood(): List<FoodEntity>

    @Query(" SELECT * FROM food WHERE id=:foodId")
    fun getFoodById(foodId:String) : FoodEntity
}