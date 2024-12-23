package com.example.wannacook

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.wannacook.ui.theme.latoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {

    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var searchItem by remember{ mutableStateOf("") }

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
                            text = "WannaCook",
                            fontFamily = latoFontFamily
                            )
                        FloatingActionButton(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .size(50.dp),
                            onClick = { /*TODO*/ },
                            containerColor = Color(0xFFF3F3F3),
                        ){
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.user), 
                                contentDescription = "notifications")
                        }
                    }
                    Spacer(modifier = Modifier.size(0.03 * screenHeight))
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ){
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
                                        fontFamily = latoFontFamily,)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(0.03 * screenHeight))
                        OutlinedTextField(
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = Color(0xFF9E9E9E),
                                containerColor = Color(0xFFF5F5F5),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(50))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(50)
                                ),
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier.size(22.dp),
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "search")
                            },
                            value = searchItem,
                            onValueChange = {searchItem = it},
                            placeholder = {
                                Text(
                                    color = Color(0xFF9E9E9E),
                                    text = "Search for recipes",
                                    fontFamily = latoFontFamily,
                                )
                            }
                        )
                        Spacer(modifier = Modifier.size(0.03 * screenHeight))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50),
                                        ),
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
                                            Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50),
                                        ),
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
                                            Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50),
                                        ),
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
                                            Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50),
                                        ),
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
                                            Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .clip(
                                            shape = RoundedCornerShape(50)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFFF5F5F5),
                                            shape = RoundedCornerShape(50),
                                        ),
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
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        ) {
                            for (recipe in recipes){
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(10))
                                        .height(0.42 * screenHeight)
                                ){
                                    Log.d("##Recipe", recipe.images[0])
                                    AsyncImage(
                                        model = recipe.images[0], // Assuming this is your Firebase URL
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(
                                                width = 0.64 * screenWidth,
                                                height = 0.42 * screenHeight
                                            ),
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.home), // Add an error placeholder
                                        placeholder = painterResource(id = R.drawable.search) // Add a loading placeholder
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
                                                start = 0.05 * screenWidth,
                                                top = 0.05 * screenWidth,
                                                bottom = 0.08 * screenWidth
                                            ),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(shape = RoundedCornerShape(50))
                                                .size(width = 120.dp, height = 38.dp)
                                                .background(
                                                    color = Color(0x7EFFFFFF)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ){
                                            Text(
                                                text = recipe.type,
                                                color = Color.Black,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp,)
                                        }
                                        Column(
                                            modifier = Modifier.fillMaxWidth()
                                        ){
                                            Text(
                                                text = recipe.name,
                                                color = Color.White,
                                                fontFamily = latoFontFamily,
                                                fontSize = 22.sp,
                                            )
                                            HorizontalDivider(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .size(height = 0.dp, width = 0.52 * screenWidth),
                                                color = Color(0x4BE0E0E0),
                                                thickness = 2.dp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(0.03 * screenWidth))
                            }
                        }
                        Spacer(modifier = Modifier.size(0.03 * screenHeight))
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