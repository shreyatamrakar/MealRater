package com.example.mealrater

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealrater.dao.MealDao
import com.example.mealrater.model.Meal

@Database(
    entities = [Meal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao
}