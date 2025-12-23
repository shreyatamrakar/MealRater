package com.example.mealrater.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mealrater.model.Meal

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(meal: Meal): Long

    @Query("SELECT * FROM meal")
    suspend fun getAllMeal(): List<Meal>
}