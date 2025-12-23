package com.example.mealrater.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val restaurant: String,
    val dish: String,
    val rating: String
)