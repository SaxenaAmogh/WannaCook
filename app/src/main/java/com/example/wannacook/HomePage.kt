package com.example.wannacook

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun HomePage(navController: NavController) {

    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val updatedRecipe = remember { mutableStateOf(mutableListOf<Recipe>()) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var searchItem by remember{ mutableStateOf("") }
    var type by remember{ mutableStateOf("Breakfast") }
    val userPrefManager = UserManager(context)

    fun filterRecipesByType(selectedType: String) {
        updatedRecipe.value = recipes.filter { it.type == selectedType }.toMutableList()
    }
    filterRecipesByType(type)

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.White)
            ){
                Column(
                    modifier = Modifier
                        .padding(
                            start = 0.035 * screenWidth,
                            end = 0.035 * screenWidth,
                        )
                        .verticalScroll(rememberScrollState())
                ){
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
                            onClick = {
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                                userPrefManager.clearUserInfo()
                                activity.finish()
                                      },
                            containerColor = Color(0xFFF3F3F3),
                        ){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.user), 
                                contentDescription = "notifications")
                        }
                    }
                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                    Column{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(15))
                                .size(width = screenWidth, height = 0.23 * screenHeight)
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.homebackgr1),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Black.copy(alpha = 0.9f), // Left edge
                                                Color.Transparent,             // Center
                                                Color.Black.copy(alpha = 0.25f) // Right edge
                                            )
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(
                                        start = 0.05 * screenWidth,
                                        top = 0.08 * screenWidth,
                                        bottom = 0.08 * screenWidth
                                    ),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(){
                                    Text(
                                        text = "Making Dinner?",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontFamily = latoFontFamily
                                    )
                                    Text(
                                        modifier = Modifier.padding(end = 0.3 * screenWidth),
                                        text ="Get the best recipes here!",
                                        fontFamily = latoFontFamily,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        maxLines = 2,
                                    )
                                }
                                FloatingActionButton(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(50))
                                        .size(width = 120.dp, height = 35.dp),
                                    containerColor = Color.White,
                                    onClick = { /*TODO*/ },
                                ){
                                    Text(
                                        text = "Explore Now",
                                        color = Color.Black,
                                        fontFamily = latoFontFamily,)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(0.03 * screenHeight))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val color by remember { mutableStateOf(Color(0xFFF5F5F5)) }
                            val selectedColor by remember { mutableStateOf(Color(0xBE249C09)) }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            if (type == "Breakfast") selectedColor else color,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (type == "Breakfast") selectedColor else color,
                                            shape = RoundedCornerShape(50),
                                        )
                                        .clickable{
                                            type = "Breakfast"
                                            filterRecipesByType(type)
                                        },
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = R.drawable.breakfast),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Breakfast",
                                    fontSize = 14.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            if (type == "Lunch") selectedColor else color,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (type == "Lunch") selectedColor else color,
                                            shape = RoundedCornerShape(50),
                                        )
                                        .clickable {
                                            type = "Lunch"
                                            filterRecipesByType(type)
                                                   },
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = R.drawable.lunch),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Lunch",
                                    fontSize = 14.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            if (type == "FastFood") selectedColor else color,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (type == "FastFood") selectedColor else color,
                                            shape = RoundedCornerShape(50),
                                        )
                                        .clickable {
                                            type = "FastFood"
                                            filterRecipesByType(type)
                                                   },
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = R.drawable.fastfood),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Fast Food",
                                    fontSize = 14.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            if (type == "Dinner") selectedColor else color,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (type == "Dinner") selectedColor else color,
                                            shape = RoundedCornerShape(50),
                                        )
                                        .clickable {
                                            type = "Dinner"
                                            filterRecipesByType(type)
                                                   },
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = R.drawable.dinner),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Dinner",
                                    fontSize = 14.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            if (type == "Desert") selectedColor else color,
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (type == "Desert") selectedColor else color,
                                            shape = RoundedCornerShape(50),
                                        )
                                        .clickable {
                                            type = "Desert"
                                            filterRecipesByType(type)
                                                   },
                                    contentAlignment = Alignment.Center
                                ){
                                    Image(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(id = R.drawable.desert),
                                        contentDescription = ""
                                    )
                                }
                                Text(
                                    text = "Desert",
                                    fontSize = 14.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(0.03 * screenHeight))
                        Column(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        ) {
                            val range:Int = updatedRecipe.value.size
                            for (x in 0 until range step 2){
                                val recipe1 = updatedRecipe.value[x]
                                Row(){
                                    Box(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(10))
                                            .height(0.32 * screenHeight)
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
                                                    .size(width = 100.dp, height = 35.dp)
                                                    .background(
                                                        color = Color(0x7EFFFFFF)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Text(
                                                    text = recipe1.type,
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
                                                            text = recipe1.likes.toString() ?: "",
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
                                                .height(0.32 * screenHeight)
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
                                                        .size(width = 100.dp, height = 35.dp)
                                                        .background(
                                                            color = Color(0x7EFFFFFF)
                                                        ),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = recipe2.type,
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
                                                                text = recipe1.likes.toString() ?: "",
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
                                Spacer(modifier = Modifier.size(0.03 * screenHeight))
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
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.home),
                            tint = Color(0xFF000000),
                            contentDescription = "Home",)
                    }
                    IconButton(onClick = {
                        navController.navigate("recipePage"){
                            popUpTo("homePage"){
                                inclusive = true
                            }
                        }
                        //Log.e("@@@@", RecipeRepo.recipeList[1].toString())
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.news_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Recipes")
                    }
                    IconButton(onClick = { }) {
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
                            painter = painterResource(id = R.drawable.user_na),
                            tint = Color(0xFF818183),
                            contentDescription = "Profile")
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomePage(navController = rememberNavController())
}