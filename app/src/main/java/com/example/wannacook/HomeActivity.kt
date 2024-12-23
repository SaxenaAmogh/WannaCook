package com.example.wannacook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wannacook.ui.theme.WannaCookTheme

data class Recipe(
    val id: Int,
    val images: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val likes: Int = 0,
    val name: String,
    val recipe: List<String> = emptyList(),
    val type : String,
)

class HomeActivity: ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()

            WannaCookTheme {
                val recipes by viewModel.recipes
                val isLoading by viewModel.isLoading

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
    }
}