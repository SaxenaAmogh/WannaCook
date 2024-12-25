package com.example.wannacook

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
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
import coil3.compose.rememberAsyncImagePainter
import com.example.wannacook.ui.theme.latoFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipePage(navController: NavController){

    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
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
                            onClick = { /*TODO*/ },
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
                        OutlinedTextField(
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = Color(0xFF9E9E9E),
                                containerColor = Color(0xFFF5F5F5),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(40))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(40)
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
                        Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        for (recipe in recipes){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(10))
                                    .size(width = screenWidth, height = 0.25 * screenHeight)
                                    .clickable { navController.navigate("recipeDetailPage/${recipe.id}")}
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
                                        ){
                                            Text(
                                                text = recipe.type,
                                                color = Color.Black,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,)
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
                                            .size(width = 120.dp, height = 35.dp),
                                        containerColor = Color.White,
                                        onClick = { navController.navigate("recipeDetailPage/${recipe.id}") },
                                    ) {
                                        Text(
                                            text = "Explore Now",
                                            color = Color.Black,
                                            fontFamily = latoFontFamily,
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.size(0.02 * screenHeight))
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
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.news),
                            tint = Color(0xFF000000),
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
fun RecipePagePreview() {
    RecipePage(rememberNavController())
}