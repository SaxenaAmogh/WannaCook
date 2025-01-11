package com.example.wannacook

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wannacook.ui.theme.latoFontFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignupPageMain(navController: NavController) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    var login by remember { mutableStateOf(true) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF222122),// Start color
                                Color.White   // End color
                            ),
                        )
                    )
            ){
                Column(
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding() + 0.04 * screenHeight,
                        start = 0.05 * screenWidth,
                        end = 0.05 * screenWidth,)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(

                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FloatingActionButton(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(50))
                                .size(50.dp),
                            onClick = { /*TODO*/ },
                            containerColor = Color(0xD2F3F3F3),
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "menu"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(0.05 * screenHeight))
                    Text(
                        text = "Go ahead and set up your account",
                        color = Color.White,
                        fontSize = (0.09*screenWidth).value.sp,
                        fontFamily = latoFontFamily,
                        lineHeight = (0.09*screenWidth).value.sp
                    )
                    Spacer(modifier = Modifier.height(0.015 * screenHeight))
                    Text(
                        text = "Sign-in to explore the world of cooking with all kinds of recipes and ingredients",
                        color = Color(0xA9FFFFFF),
                        fontSize = (0.04*screenWidth).value.sp,
                        fontFamily = latoFontFamily,
                        lineHeight = (0.04*screenWidth).value.sp
                    )
                    Spacer(modifier = Modifier.height(0.05 * screenHeight))
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xD2F3F3F3),
                            shape = RoundedCornerShape(11)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 0.05 * screenWidth,
                                end = 0.05 * screenWidth,
                                top = 0.035 * screenHeight
                            )
                            .height(0.075 * screenHeight)
                            .fillMaxWidth()
                            .background(
                                color = Color(0x2D000000),
                                shape = RoundedCornerShape(50)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ){
                        Button(
                            modifier = Modifier
                                .padding(0.01 * screenWidth)
                                .fillMaxHeight()
                                .clip(
                                    shape = RoundedCornerShape(50)
                                )
                                .width(0.45 * screenWidth),
                            onClick = { login = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (login) Color(0xFFF3F3F3) else Color.Transparent,
                            )
                        ) {
                            Text(
                                text = "Login",
                                color = if (login) Color(0xFF000000) else Color(0x8A000000),
                                fontSize = (0.05 * screenWidth).value.sp,
                                fontFamily = latoFontFamily,
                                lineHeight = (0.05 * screenWidth).value.sp
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(0.01 * screenWidth)
                                .fillMaxHeight()
                                .clip(
                                    shape = RoundedCornerShape(50)
                                )
                                .width(0.45 * screenWidth),
                            onClick = { login = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (login) Color.Transparent else Color(0xFFF3F3F3),
                            )
                        ) {
                            Text(
                                text = "Register",
                                color = if (login) Color(0x8A000000) else Color(0xFF000000),
                                fontSize = (0.05 * screenWidth).value.sp,
                                fontFamily = latoFontFamily,
                                lineHeight = (0.05 * screenWidth).value.sp
                            )
                        }
                    }
                    if (login) {
                        Login()
                    } else{
                        Register()
                    }
                }
            }
        }
    )
}

@Composable
fun Login(){

    val context = LocalContext.current
    val activity = context as ComponentActivity
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val userPrefManager = UserManager(context) // 'this' is the context
    val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = Firebase.auth

    fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val userDocRef = db.collection("users").document(email)

                    userDocRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                // Fetch the username field from the document
                                val username = documentSnapshot.getString("username")
                                val liked = documentSnapshot.get("liked") as List<Int>
                                if (username != null) {
                                    userPrefManager.saveUserInfo(username, email, liked)
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error fetching user by email: ", e)
                        }
                }
            }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 0.05 * screenWidth,
                end = 0.05 * screenWidth,
                top = 0.03 * screenHeight
            ),
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07 * screenHeight),
            leadingIcon = {
                Icon(
                    Icons.Filled.MailOutline,
                    contentDescription = "email"
                )
            },
            placeholder = {
                Text("Email Address")
            },
            value = email,
            onValueChange = {email = it},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF000000),
                unfocusedBorderColor = Color(0x41000000),
                focusedTextColor = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(0.015 * screenHeight))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07 * screenHeight),
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = "email"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.hide),
                    contentDescription = "email"
                )
            },
            placeholder = {
                Text("Password")
            },
            value = password,
            onValueChange = {password = it},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF000000),
                unfocusedBorderColor = Color(0x41000000),
                focusedTextColor = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(0.005 * screenHeight))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Forgot Password?",
            color = Color(0xFF000000),
            textAlign = TextAlign.End,
            fontFamily = latoFontFamily,
        )
        Spacer(modifier = Modifier.height(0.03 * screenHeight))
        FloatingActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(50)),
            onClick = {
                loginUser(email, password)
                val intent = Intent(activity, HomeActivity::class.java)
                context.startActivity(intent)
                activity.finish()
                      },
            containerColor = Color(0xFF1B1B1B),
        ){
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                color = Color.White,
                text = "Login",
                fontSize = 20.sp,
                fontFamily = latoFontFamily)
        }
        Spacer(modifier = Modifier.height(0.03 * screenHeight))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            HorizontalDivider(
                modifier = Modifier.width(0.3 * screenWidth),
                color = Color(0x59000000),
                thickness = 2.dp,
            )
            Text(
                modifier = Modifier.padding(0.02 * screenWidth),
                text = "Or login with",
                color = Color(0xFF000000),
                fontSize = (0.04 * screenWidth).value.sp,
                fontFamily = latoFontFamily,
                lineHeight = (0.04 * screenWidth).value.sp
            )
            HorizontalDivider(
                modifier = Modifier.width(0.3 * screenWidth),
                color = Color(0x59000000),
                thickness = 2.dp
            )
        }
        Spacer(modifier = Modifier.height(0.03 * screenHeight))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Button(
                modifier = Modifier
                    .padding(0.01 * screenWidth)
                    .clip(
                        shape = RoundedCornerShape(50)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0x405E5E5E),
                        shape = RoundedCornerShape(50)
                    )
                    .width(0.44 * screenWidth),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "google")
                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                Text(
                    text = "Google",
                    color = Color(0xFF000000),
                    fontSize = (0.045 * screenWidth).value.sp,
                    fontFamily = latoFontFamily,
                    lineHeight = (0.05 * screenWidth).value.sp
                )
            }
            Button(
                modifier = Modifier
                    .padding(0.01 * screenWidth)
                    .clip(
                        shape = RoundedCornerShape(50)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0x405E5E5E),
                        shape = RoundedCornerShape(50)
                    )
                    .width(0.44 * screenWidth),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "facebook")
                Spacer(modifier = Modifier.width(0.02 * screenWidth))
                Text(
                    text = "Facebook",
                    color = Color(0xFF000000),
                    fontSize = (0.045 * screenWidth).value.sp,
                    fontFamily = latoFontFamily,
                    lineHeight = (0.05 * screenWidth).value.sp
                )
            }
        }
    }
}

@Composable
fun Register(){

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val activity = context as ComponentActivity
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val userPrefManager = UserManager(context) // 'this' is the context

    val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = Firebase.auth

    fun registerUser(email: String, password: String, username: String) {

        val userDocRef = db.collection("users").document(email)
        val userData = mapOf(
            "username" to username,
            "email" to email,
            "password" to password,
            "liked" to listOf<Int>(),
        )

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val liked = listOf<Int>()
                    userPrefManager.saveUserInfo(username, email, liked)
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                    activity.finish()
                }
                else {
                    Log.e("@@Firebase", "Error creating user: ", task.exception)
                }
            }

        userDocRef.set(userData)
            .addOnSuccessListener {
                Log.d("Firestore", "User added successfully at /users/$username/")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error adding user: ", e)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 0.05 * screenWidth,
                end = 0.05 * screenWidth,
                top = 0.03 * screenHeight
            ),
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07 * screenHeight),
            leadingIcon = {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = "user"
                )
            },
            placeholder = {
                Text("Username")
            },
            value = username,
            onValueChange = {username = it},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF000000),
                unfocusedBorderColor = Color(0x41000000),
                focusedTextColor = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(0.015 * screenHeight))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07 * screenHeight),
            leadingIcon = {
                Icon(
                    Icons.Filled.MailOutline,
                    contentDescription = "email"
                )
            },
            placeholder = {
                Text("Email Address")
            },
            value = email,
            onValueChange = {email = it},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF000000),
                unfocusedBorderColor = Color(0x41000000),
                focusedTextColor = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(0.015 * screenHeight))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.07 * screenHeight),
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = "password"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.hide),
                    contentDescription = "see"
                )
            },
            placeholder = {
                Text("Password")
            },
            value = password,
            onValueChange = {password = it},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF000000),
                unfocusedBorderColor = Color(0x41000000),
                focusedTextColor = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(0.015 * screenHeight))
        Spacer(modifier = Modifier.height(0.03 * screenHeight))
        FloatingActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(50)),
            onClick = {
                registerUser(email, password, username)
            },
            containerColor = Color(0xFF1B1B1B),
        ){
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                color = Color.White,
                text = "Create Account",
                fontSize = 20.sp,
                fontFamily = latoFontFamily)
        }
        Spacer(modifier = Modifier.height(0.03 * screenHeight))
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPagePreview() {
    SignupPageMain(rememberNavController())
}