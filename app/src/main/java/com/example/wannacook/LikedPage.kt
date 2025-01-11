package com.example.wannacook

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.wannacook.ui.theme.latoFontFamily

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedPage(navController: NavController) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val userPrefManager = UserManager(context)
    var searchItem by remember { mutableStateOf("") }
    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
    val updatedRecipe = remember { mutableStateOf(mutableListOf<Recipe>()) }

    val liked = userPrefManager.getUserInfo()["liked"] as List<Int>
    Log.d("##Numbers", recipes.toString())

    fun filterRecipesByTypes(liked: List<Int>) {
        updatedRecipe.value = mutableListOf()

        for (i in liked) {
            val recipe = recipes.find { it.id == i }
            if (recipe != null) {
                updatedRecipe.value.add(recipe)
            }
        }
    }

    if(liked.isNotEmpty()) {
        filterRecipesByTypes(liked)
    }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 0.035 * screenWidth,
                            end = 0.035 * screenWidth,
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 0.03 * screenWidth,
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FloatingActionButton(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .size(50.dp),
                            onClick = { /*TODO*/ },
                            containerColor = Color(0xFFF3F3F3),
                        ){
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                        Text(
                            fontSize = 24.sp,
                            text = "WannaCook?!",
                            fontFamily = latoFontFamily
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .size(50.dp),
                            onClick =
                            {
                                navController.navigate("profilePage")
                            },
                            containerColor = Color(0xFFF3F3F3),
                        ){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.user),
                                contentDescription = "notifications")
                        }
                    }
                    Spacer(modifier = Modifier.size(0.015 * screenHeight))
                    Column{
                        if (updatedRecipe.value.isNotEmpty()) {
                            for (recipe in updatedRecipe.value) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(shape = RoundedCornerShape(10))
                                        .size(width = screenWidth, height = 0.25 * screenHeight)
                                        .clickable { navController.navigate("recipeDetailPage/${recipe.id}") }
                                ) {
                                    AsyncImage(
                                        model = recipe.images[1], // Assuming this is your Firebase URL
                                        contentDescription = null, // Add a loading placeholder
                                        contentScale = ContentScale.Crop,
                                    )
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Black.copy(alpha = 0.9f), // Left edge
                                                        Color.Transparent,             // Center
                                                        Color.Black.copy(alpha = 0.2f) // Right edge
                                                    )
                                                )
                                            )
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(
                                                start = 0.05 * screenWidth,
                                                top = 0.06 * screenWidth,
                                                bottom = 0.06 * screenWidth
                                            ),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column() {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = RoundedCornerShape(35))
                                                    .size(width = 90.dp, height = 30.dp)
                                                    .background(
                                                        color = Color(0x7EFFFFFF)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = recipe.type,
                                                    color = Color.Black,
                                                    fontFamily = latoFontFamily,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                )
                                            }
                                            Text(
                                                text = recipe.name,
                                                color = Color(0xFFFFFFFF),
                                                fontSize = 24.sp,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        FloatingActionButton(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(50))
                                                .size(width = 140.dp, height = 35.dp),
                                            containerColor = Color.White,
                                            onClick = { navController.navigate("recipeDetailPage/${recipe.id}") },
                                        ) {
                                            Row{
                                                Icon(
                                                    modifier = Modifier.size(20.dp),
                                                    painter = painterResource(id = R.drawable.heart),
                                                    tint = Color.Red,
                                                    contentDescription = "Saved")
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    text = "Liked by You",
                                                    color = Color.Black,
                                                    fontFamily = latoFontFamily,
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.02 * screenHeight))
                            }
                        }else{
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.35 * screenHeight)
                                    .clip(shape = RoundedCornerShape(10))
                                    .background(
                                        Color(0xFFF3F3F3)
                                    ),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "No recipes to show :(",
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0x97000000),
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Transparent,
                modifier = Modifier.shadow(
                    elevation = 16.dp, // Shadow elevation
                    shape = RectangleShape // Applies shadow to the full bar
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    IconButton(onClick = {
                        navController.navigate("homePage"){
                            popUpTo("recipePage"){
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.home_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Home",)
                    }
                    IconButton(onClick = {
                        navController.navigate("recipePage"){
                        popUpTo("homePage"){
                            inclusive = true
                        }
                    }}) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.news_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Recipes")
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.heart),
                            tint = Color(0xFF000000),
                            contentDescription = "Saved")
                    }
                    IconButton(onClick = {
                        navController.navigate("profilePage")
                    }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.user_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Profile")
                    }
                }
            }
        }
    )
}