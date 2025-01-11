package com.example.wannacook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wannacook.ui.theme.WannaCookTheme

data class Recipe(
    val description: String,
    val difficulty: String,
    val id: Int,
    val images: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val likes: Int = 0,
    val name: String,
    val quantity: List<String> = emptyList(),
    val recipe: List<String> = emptyList(),
    val rid: String,
    val time: Int,
    val type : String,
)

class HomeActivity: ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setContent {

            WannaCookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NavigateHome()
                }
            }
        }
    }
}

@Composable
fun NavigateHome(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homePage") {
        composable("homePage") {
            HomePage(navController)
        }
        composable("recipePage") {
            RecipePage(navController)
        }
        composable(
            "recipeDetailPage/{selectedIndex}",
            arguments = listOf(navArgument("selectedIndex") {
                type = NavType.IntType
                defaultValue = 1 // Default value in case none is passed
            })
        ) { backStackEntry ->
            val selectedIndex =
                backStackEntry.arguments?.getInt("selectedIndex") ?: 1
            RecipeDetailPage(navController, selectedIndex)
        }
        composable("profilePage") {
            ProfilePage(navController)
        }
        composable("likedPage") {
            LikedPage(navController)
        }
    }
}