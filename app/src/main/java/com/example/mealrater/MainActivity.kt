package com.example.mealrater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mealrater.uicomponent.MainMealScreen
import com.example.mealrater.uicomponent.RatingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // creating an in-memory version of your Room database
        val db = Room.inMemoryDatabaseBuilder(
            applicationContext,
            AppDatabase::class.java
        ).build()

        enableEdgeToEdge()
        setContent {
            MaterialTheme { // using default theme for now
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MealRaterApp(db = db)
                }
            }
        }
    }
}


@Composable
fun MealRaterApp(db: AppDatabase? = null) {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(navController = navController, startDestination = "main") {
            composable("main") { MainMealScreen(navController, db) }
            composable("rate") { RatingScreen(navController) }
        }
    }
}

/*preview pane*//*

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMealScreenScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        MainMealScreen(navController)
    }
}*/
