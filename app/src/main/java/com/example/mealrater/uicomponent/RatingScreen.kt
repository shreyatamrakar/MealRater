package com.example.mealrater.uicomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RatingScreen(navController: NavController) {
    var selectedRating by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row {
            (1..5).forEach { num ->
                OutlinedButton(
                    onClick = { selectedRating = num },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedRating == num) Color(red = 103, green = 80, blue = 164) else Color.Transparent,
                        contentColor = if (selectedRating == num) Color.White else MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(num.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(onClick = {
                navController.popBackStack()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 103, green = 80, blue = 164),
                    contentColor = Color.White,
                )) {
                Text("Cancel")
            }

            TextButton(
                onClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("mealRating", selectedRating.toString())
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(red = 103, green = 80, blue = 164),
                    contentColor = Color.White,
                )
            ) {
                Text("Save")
            }
        }
    }
}

/*preview pane*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        RatingScreen(navController)
    }
}