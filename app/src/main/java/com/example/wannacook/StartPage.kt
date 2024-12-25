package com.example.wannacook

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wannacook.ui.theme.latoFontFamily

@Composable
fun StartPage(navController: NavController) {

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Scaffold(
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF353535),  // Start color
                            Color.White   // End color
                        ),
                    )
                ),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Image(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                            .size(1.1 * screenWidth)
                            .offset(x = (-50).dp),
                        painter = painterResource(id = R.drawable.mainpage),
                        contentScale = ContentScale.Crop,
                        contentDescription = "")
//                    Spacer(modifier = Modifier.size(0.05 * screenHeight))
                    Column(
                        modifier = Modifier.padding(
                            start = 0.05 * screenWidth,
                            end = 0.05 * screenWidth),
                    ) {
                        Text(
                            text = "Cooking",
                            color = Color(0xFF1B1B1B),
                            fontSize = (0.078 * screenHeight).value.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                            lineHeight = (0.07 * screenHeight).value.sp
                        )
                        Text(
                            modifier = Modifier.offset(y = (-0.02 * screenHeight)),
                            text = "Delicious",
                            color = Color(0x61000000),
                            fontSize = (0.078 * screenHeight).value.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Row(
                            modifier = Modifier.offset(y = (-0.033 * screenHeight)),
                        ) {
                            Text(
                                text = "Like a ",
                                color = Color(0x61000000),
                                fontSize = (0.078 * screenHeight).value.sp,
                                fontFamily = latoFontFamily,
                                fontWeight = FontWeight.Bold,
                                )
                            Text(
                                text = "Chef",
                                color = Color(0x61000000),
                                fontSize = (0.078 * screenHeight).value.sp,
                                fontFamily = latoFontFamily,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                        Text(
                            modifier = Modifier.offset(y = (-0.015* screenHeight)),
                            text = "This app offers a wide selection of diverse & easy recipes suitable for all cooking levels!",
                            color = Color(0x61000000),
                            fontSize = (0.02 * screenHeight).value.sp,
                            fontFamily = latoFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.size(0.015 * screenHeight))
                        FloatingActionButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(50)),
                            onClick = {
//                                val intent = Intent(activity, HomeActivity::class.java)
//                                context.startActivity(intent)
                                navController.navigate("SignupPage")
                            },
                            containerColor = Color(0xFF1B1B1B),
                        ){
                            Text(
                                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                                color = Color.White,
                                text = "Get Started",
                                fontSize = 20.sp,
                                fontFamily = latoFontFamily)
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    StartPage(navController = rememberNavController())
}
