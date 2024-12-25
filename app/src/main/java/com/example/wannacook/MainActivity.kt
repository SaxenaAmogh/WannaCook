package com.example.wannacook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wannacook.ui.theme.WannaCookTheme

private lateinit var userPrefManager: UserManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userPrefManager = UserManager(this)
        setContent {
            WannaCookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    NavigateMain()
                }
            }
        }
    }
}

@Composable
fun NavigateMain() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "startPage"){
        composable("startPage"){
            StartPage(navController)
        }
        composable("homePage"){
            HomePage(navController)
        }
        composable("recipePage") {
            RecipePage(navController)
        }
        composable("SignupPage"){
            SignupPageMain(navController)
        }
//        composable("LoginPage"){
//            LoginPageMain(navController)
//        }
//        composable("CongratsPage"){
//            CongratsScreen(navController)
//        }
    }
}
