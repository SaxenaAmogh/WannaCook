package com.example.wannacook

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.wannacook.ui.theme.latoFontFamily
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RecipeDetailPage(navController: NavController, selectedIndex: Int){

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var liked by remember { mutableStateOf(false) }
    var recipe : Recipe? = null
    val viewModel: MainViewModel = viewModel()
    val recipes by viewModel.recipes
    for (x in recipes) {
        Log.d("@@12", "Recipe: $selectedIndex")
        if (x.id == selectedIndex) {
            recipe = x
        }
    }

    fun likeRecipe(){
        val userPrefManager = UserManager(context)
        val user = userPrefManager.getUserInfo()
        val email = user["email"].toString()
        val db = FirebaseFirestore.getInstance()
        val recipeDocRef = recipe?.let { db.collection("recipes").document(it.rid) }
        val userDocRef = db.collection("users").document(email)

        if (liked) {
            recipeDocRef?.update("likes", recipe?.likes?.plus(1))
            userDocRef.update("liked", com.google.firebase.firestore.FieldValue.arrayUnion(selectedIndex))
            userPrefManager.updateLikes(newValue = selectedIndex)
        } else {
            recipeDocRef?.update("likes", recipe?.likes?.minus(1))
            userDocRef.update("liked", com.google.firebase.firestore.FieldValue.arrayRemove(selectedIndex))
            userPrefManager.updateLikes(removeValue = selectedIndex)
        }
    }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(screenWidth, 0.4f * screenHeight)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxWidth(),
                            model = recipe?.images?.get(1), // Assuming this is your Firebase URL
                            contentDescription = null, // Add a loading placeholder
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.0f), // Left edge
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.Transparent,
                                            Color.White.copy(alpha = 1f) // Right edge
                                        )
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = innerPadding.calculateTopPadding(),
                                    start = 0.035 * screenWidth,
                                    end = 0.035 * screenWidth,
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            FloatingActionButton(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(50))
                                    .size(50.dp),
                                onClick = {
                                    navController.popBackStack()
                                },
                                containerColor = Color(0xFFF3F3F3),
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "menu"
                                )
                            }
                            val user = UserManager(context).getUserInfo()
                            val likes = user["liked"] as List<Int>
                            for (x in likes) {
                                if (x == selectedIndex) {
                                    liked = true
                                    break
                                }
                            }
                            FloatingActionButton(
                                modifier = Modifier
                                    .clip(shape = RoundedCornerShape(50))
                                    .size(50.dp),
                                onClick = {
                                    liked = !liked
                                    likeRecipe()
                                },
                                containerColor = Color(0xFFF3F3F3),
                            ) {
                                if (liked){
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(id = R.drawable.heart),
                                        tint = Color.Red,
                                        contentDescription = "like"
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(id = R.drawable.heart_na),
                                        contentDescription = "like"
                                    )
                                }

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(0.02 * screenHeight))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 0.035 * screenWidth,
                                end = 0.035 * screenWidth,
                                bottom = innerPadding.calculateBottomPadding(),
                            ),
                    ) {
                        Text(
                            text = recipe?.name ?: "",
                            fontSize = (0.05 * screenHeight).value.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Medium,
                        )
                        Spacer(modifier = Modifier.height(0.005 * screenHeight))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.clock),
                                    contentDescription = "time"
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = recipe?.time.toString() + " mins",
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.ingred),
                                    contentDescription = "ingred"
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = recipe?.ingredients?.size.toString() + " Ingredients",
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.bars),
                                    contentDescription = "difficulty"
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = recipe?.difficulty ?: "",
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter = painterResource(id = R.drawable.heart),
                                    contentDescription = "likes",
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = recipe?.likes.toString() ?: "",
                                    fontSize = 16.sp,
                                    fontFamily = latoFontFamily,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(0.017 * screenHeight))
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = Color(0x72C0BFBF),
                            thickness = 2.dp
                        )
                        Spacer(modifier = Modifier.height(0.017 * screenHeight))
                        Column {
                            var expanded by remember { mutableStateOf(false) }
                            val minimizedMaxLines = 3
                            recipe?.description?.let {
                                Text(
                                    text = it.trimIndent(),
                                    maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
                                    fontFamily = latoFontFamily,
                                    fontSize = 16.sp,
                                    color = Color(0xFF6F6E6E),
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                            Text(
                                text = if (expanded) "Read Less" else "Read More",
                                color = Color(0xFF4CAF50),
                                fontFamily = latoFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        }
                        Spacer(modifier = Modifier.height(0.017 * screenHeight))
                        Text(
                            text = "Ingredients", //recipe?.difficulty ?: "",
                            fontSize = 18.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(0.005 * screenHeight))
                        Column {
                            val ingredients:Int = recipe?.ingredients?.size?.toInt() ?: 0
                            for (x in 0..<ingredients) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = "${x+1}. ",
                                            color = Color(0xFF6F6E6E),
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Normal,
                                        )
                                        Spacer(modifier = Modifier.width(0.015 * screenWidth))
                                        if (recipe != null) {
                                            Text(
                                                text = recipe.ingredients[x],
                                                color = Color(0xFF6F6E6E),
                                                fontSize = 16.sp,
                                                fontFamily = latoFontFamily,
                                                fontWeight = FontWeight.Normal,
                                            )
                                        }
                                    }
                                    if (recipe != null) {
                                        Text(
                                            text = recipe.quantity[x],
                                            color = Color(0xFF6F6E6E),
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.005 * screenHeight))
                            }
                        }
                        Spacer(modifier = Modifier.height(0.012 * screenHeight))
                        Text(
                            text = "Instructions", //recipe?.difficulty ?: "",
                            fontSize = 18.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(0.005 * screenHeight))
                        Column {
                            val recipeS:Int = recipe?.recipe?.size?.toInt() ?: 0
                            for (x in 0..<recipeS) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "${x+1}. ",
                                        color = Color(0xFF6F6E6E),
                                        fontSize = 16.sp,
                                        fontFamily = latoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                    )
                                    Spacer(modifier = Modifier.width(0.015 * screenWidth))
                                    if (recipe != null) {
                                        Text(
                                            color = Color(0xFF6F6E6E),
                                            text = recipe.recipe[x],
                                            fontSize = 16.sp,
                                            fontFamily = latoFontFamily,
                                            fontWeight = FontWeight.Normal,

                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(0.005 * screenHeight))
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipePreview() {
    RecipeDetailPage(rememberNavController(), selectedIndex = 0)
}