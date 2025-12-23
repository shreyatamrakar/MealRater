package com.example.mealrater.uicomponent

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mealrater.AppDatabase
import com.example.mealrater.model.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainMealScreen(navController: NavController, db: AppDatabase? = null) {
    var restaurantName by rememberSaveable  { mutableStateOf("") }
    var dishName by rememberSaveable  { mutableStateOf("") }
    var rating by rememberSaveable  { mutableStateOf("Label") }
    var statusMessage by rememberSaveable  { mutableStateOf("") }

    // Observe rating from backStack
    val result = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("mealRating")
        ?.observeAsState()

    result?.value?.let {
        rating = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*restaurant input section*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Restaurant:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 8.dp)
            )

            OutlinedTextField(
                value = restaurantName,
                onValueChange = { restaurantName = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        /*dish input section*/
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Dish:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 8.dp)
            )

            OutlinedTextField(
                value = dishName,
                onValueChange = { dishName = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Rating: $rating")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("rate") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 103, green = 80, blue = 164),
                contentColor = Color.White,
            )
        ) {
            Text("Rate Meal")
        }

       /* val result = navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>("mealRating")
            ?.observeAsState()

        result?.value?.let {
            rating = it
        }*/

        Spacer(modifier = Modifier.height(20.dp))

        /*Save button section*/
        Button(
            onClick = {
                saveMealData(
                    db = db,
                    restaurant = restaurantName,
                    dish = dishName,
                    rating = rating,
                    onResult = { statusMessage = it }
                )
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 103, green = 80, blue = 164),
                contentColor = Color.White,

                ),
            shape = RectangleShape
        ) {
            Text(
                text = "Save",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = statusMessage,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.9f)
        )
    }
}


fun saveMealData(
    db: AppDatabase?,
    restaurant: String,
    dish: String,
    rating: String,
    onResult: (String) -> Unit
) {
    if (db == null) {
        onResult("Database not initialized")
        return
    }

    if (restaurant.isBlank() || dish.isBlank() || rating == "Label") {
        onResult("All fields must be filled.")
        return
    }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val meal = Meal(restaurant = restaurant, dish = dish, rating = rating)
            val insertedId = db.mealDao().insertMeal(meal)
            Log.d("MealSave", "Meal saved: $meal")
            Log.d("MealSave", "Meal saved with id: $insertedId")
            onResult("Meal saved successfully!")

            val savedMeal = Meal(id = insertedId.toInt(), restaurant = restaurant, dish = dish, rating = rating)
            Log.d("MealSave", "Meal saved with ID: $insertedId, full object: $savedMeal")


            val allMeals = db.mealDao().getAllMeal()
            Log.d("MealSave", "All meals: $allMeals")

        } catch (e: Exception) {
            onResult("Failed to save meal: ${e.localizedMessage}")
        }
    }
}

/*preview pane*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMealScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        MainMealScreen(navController = navController, db = null)
    }
}
