 package com.example.wannacook

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.wannacook.ui.theme.latoFontFamily

 @SuppressLint("MutableCollectionMutableState")
@Composable
fun ProfilePage(navController: NavController) {

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val userPrefManager = UserManager(context)
    var randomElement1 by remember { mutableIntStateOf(1) }
    var randomElement2 by remember { mutableIntStateOf(2) }
    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
    val updatedRecipe = remember { mutableStateOf(mutableListOf<Recipe>()) }
    var counter by remember { mutableStateOf(false) }

    val numbers = userPrefManager.getUserInfo()["liked"] as List<Int>
    Log.d("##Numbers", recipes.toString())
    if (numbers.size >= 2) {
        randomElement1 = numbers[0]
        randomElement2 = numbers[1]
        counter = true
    }

    fun filterRecipesByTypes(selectedType1: Int, selectedType2: Int) {
        updatedRecipe.value = mutableListOf()

        val recipe1 = recipes.find { it.id == selectedType1 }
        if (recipe1 != null) {
            updatedRecipe.value.add(recipe1)
        }

        val recipe2 = recipes.find { it.id == selectedType2 }
        if (recipe2 != null) {
            updatedRecipe.value.add(recipe2)
        }
    }

    filterRecipesByTypes(randomElement1, randomElement2)

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
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 0.03 * screenWidth,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            fontSize = 24.sp,
                            text = "WannaCook?!",
                            fontFamily = latoFontFamily,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        FloatingActionButton(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .size(50.dp)
                                .align(Alignment.CenterEnd),
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
                    Spacer(modifier = Modifier.size(0.01 * screenHeight))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "profile",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(0.45 * screenWidth)
                                .clip(shape = RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            text = "Hello, ${userPrefManager.getUserInfo()["userName"]}!",
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            text = userPrefManager.getUserInfo()["email"].toString(),
                            fontFamily = latoFontFamily,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                    if (counter) {
                        val range:Int = updatedRecipe.value.size
                        Log.d("###Range", range.toString())
                        for (x in 0 until range-1){
                            val recipe1 = updatedRecipe.value[x]
                            Row{
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(10))
                                        .height(0.28 * screenHeight)
                                        .width(0.45 * screenWidth)
                                        .clickable {
                                            navController.navigate("recipeDetailPage/${recipe1.id}")
                                        }
                                ){
                                    Log.d("##Recipe", recipe1.images[0])
                                    AsyncImage(
                                        model = recipe1.images[0], // Assuming this is your Firebase URL
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(
                                                width = 0.64 * screenWidth,
                                                height = 0.42 * screenHeight
                                            ),
                                        contentScale = ContentScale.Crop,
                                    )
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Black.copy(alpha = 0.5f), // Left edge
                                                        Color.Transparent,             // Center
                                                        Color.Black.copy(alpha = 0.8f) // Right edge
                                                    )
                                                )
                                            )
                                    )
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(
                                                start = 0.03 * screenWidth,
                                                end = 0.03 * screenWidth,
                                                top = 0.05 * screenWidth,
                                                bottom = 0.04 * screenWidth
                                            ),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(50))
                                                .size(width = 120.dp, height = 35.dp)
                                                .background(
                                                    color = Color(0x7EFFFFFF)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ){
                                            Text(
                                                text = "Liked by you",
                                                color = Color.Black,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,)
                                        }
                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                        ){
                                            Text(
                                                text = recipe1.name,
                                                color = Color.White,
                                                fontFamily = latoFontFamily,
                                                fontSize = 20.sp,
                                            )
                                            HorizontalDivider(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                color = Color(0x4BE0E0E0),
                                                thickness = 2.dp
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Start,
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Image(
                                                        modifier = Modifier.size(16.dp),
                                                        painter = painterResource(id = R.drawable.clock),
                                                        contentDescription = "time"
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(
                                                        text = recipe1.time.toString() + " mins",
                                                        color = Color.White,
                                                        fontSize = 14.sp,
                                                        fontFamily = latoFontFamily,
                                                        fontWeight = FontWeight.Normal,
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(15.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        modifier = Modifier.size(18.dp),
                                                        painter = painterResource(id = R.drawable.heart),
                                                        contentDescription = "likes",
                                                        tint = Color.Red
                                                    )
                                                    Spacer(modifier = Modifier.width(5.dp))
                                                    Text(
                                                        text = recipe1.likes.toString(),
                                                        color = Color.White,
                                                        fontSize = 14.sp,
                                                        fontFamily = latoFontFamily,
                                                        fontWeight = FontWeight.Normal,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.03 * screenWidth))
                                if (x+1 < updatedRecipe.value.size) {
                                    val recipe2 = updatedRecipe.value[x + 1]
                                    Box(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(10))
                                            .height(0.28 * screenHeight)
                                            .width(0.45 * screenWidth)
                                            .clickable {
                                                navController.navigate("recipeDetailPage/${recipe2.id}")
                                            }
                                    ) {
                                        Log.d("##Recipe", recipe2.images[0])
                                        AsyncImage(
                                            model = recipe2.images[0], // Assuming this is your Firebase URL
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(
                                                    width = 0.64 * screenWidth,
                                                    height = 0.42 * screenHeight
                                                ),
                                            contentScale = ContentScale.Crop,
                                        )
                                        Box(
                                            modifier = Modifier
                                                .matchParentSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Black.copy(alpha = 0.5f), // Left edge
                                                            Color.Transparent,             // Center
                                                            Color.Black.copy(alpha = 0.8f) // Right edge
                                                        )
                                                    )
                                                )
                                        )
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(
                                                    start = 0.03 * screenWidth,
                                                    end = 0.03 * screenWidth,
                                                    top = 0.05 * screenWidth,
                                                    bottom = 0.04 * screenWidth
                                                ),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(shape = RoundedCornerShape(50))
                                                    .size(width = 120.dp, height = 35.dp)
                                                    .background(
                                                        color = Color(0x7EFFFFFF)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Liked by you",
                                                    color = Color.Black,
                                                    fontFamily = latoFontFamily,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                )
                                            }
                                            Column(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = recipe2.name,
                                                    color = Color.White,
                                                    fontFamily = latoFontFamily,
                                                    fontSize = 20.sp,
                                                )
                                                HorizontalDivider(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    color = Color(0x4BE0E0E0),
                                                    thickness = 2.dp
                                                )
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Start,
                                                ) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Image(
                                                            modifier = Modifier.size(16.dp),
                                                            painter = painterResource(id = R.drawable.clock),
                                                            contentDescription = "time"
                                                        )
                                                        Spacer(modifier = Modifier.width(5.dp))
                                                        Text(
                                                            text = recipe2.time.toString() + " mins",
                                                            color = Color.White,
                                                            fontSize = 14.sp,
                                                            fontFamily = latoFontFamily,
                                                            fontWeight = FontWeight.Normal,
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(15.dp))
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            modifier = Modifier.size(18.dp),
                                                            painter = painterResource(id = R.drawable.heart),
                                                            contentDescription = "likes",
                                                            tint = Color.Red
                                                        )
                                                        Spacer(modifier = Modifier.width(5.dp))
                                                        Text(
                                                            text = recipe2.likes.toString(),
                                                            color = Color.White,
                                                            fontSize = 14.sp,
                                                            fontFamily = latoFontFamily,
                                                            fontWeight = FontWeight.Normal,
                                                        )
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(0.02 * screenHeight))
                        FloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .height(0.06 * screenHeight),
                            onClick = {
                                navController.navigate("likedPage") {
                                    popUpTo("homePage") {
                                        inclusive = true
                                    }
                                }},
                            containerColor = Color(0xFFF3F3F3),
                        ){
                            Row(
                                modifier = Modifier.padding(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = "More ",
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "back")
                            }
                        }
                    } else {
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
                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                    FloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(50)),
                        onClick = {
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            userPrefManager.clearUserInfo()
                            activity.finish()
                        },
                        containerColor = Color(0xFF1B1B1B),
                    ){
                        Text(
                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                            color = Color.White,
                            text = "Logout",
                            fontSize = 20.sp,
                            fontFamily = latoFontFamily)
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
                            popUpTo("homePage"){
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
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.news_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Recipes")
                    }
                    IconButton(onClick = {
                        navController.navigate("likedPage") {
                            popUpTo("homePage") {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.heart_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Saved")
                    }
                    IconButton(onClick = {}
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.user),
                            tint = Color(0xFF000000),
                            contentDescription = "Profile")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage(rememberNavController())
}