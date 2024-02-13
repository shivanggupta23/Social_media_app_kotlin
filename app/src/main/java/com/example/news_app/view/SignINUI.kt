package com.example.news_app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.news_app.view_model.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun SignInScreen(signInViewModel: SignInViewModel,
                 navController: NavController,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()
    var authErrorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
//    val authErrorMessage by signInViewModel.authErrorMessage.collectAsState()

    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


//with validation//
            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            signInViewModel.signIn(email, password)
                            // Sign-in successful, navigate to the next screen or perform any required action.
                            navController.navigate("dashboard")
                        } catch (e: Exception) {
                            // Handle sign-in failure (e.g., display an error message)
                            authErrorMessage = "Sign-in failed with exception: ${e.message}"
                        }
                    }
//                    if (email.isEmpty() || password.isEmpty()) {
//                        authErrorMessage = "Please fill in all fields."
//                        Toast.makeText(context,authErrorMessage,Toast.LENGTH_SHORT).show()
//                    } else {
//                        coroutineScope.launch {
//                            try {
//                                val userCredential = auth.signInWithEmailAndPassword(email, password)
//                                val user: FirebaseUser? = userCredential.await().user
//                                if (user != null) {
//                                    Log.d("SignIn", "Signin successful")
//                                    Toast.makeText(context,"Signin Successfully",Toast.LENGTH_SHORT).show()
//                                    // Sign-in successful, navigate to the next screen or perform any required action.
//                                    navController.navigate("dashboard")
//                                } else {
//                                    // Sign-in failed
//                                    authErrorMessage = "Sign-in failed. Please check your credentials."
//                                    Toast.makeText(context,authErrorMessage,Toast.LENGTH_SHORT).show()
//                                }
//                            } catch (e: Exception) {
//                                // Handle sign-in failure (e.g., display an error message)
//                                authErrorMessage = "Sign-in failed with exception: ${e.message}"
//                                Toast.makeText(context,authErrorMessage,Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign In")
            }

            //without validation//
//            Button(
//                onClick = {
//                    coroutineScope.launch {
//                        try {
//                            val userCredential = auth.signInWithEmailAndPassword(email, password)
//                            val user: FirebaseUser? = userCredential.await().user
//                            if (user != null) {
//                                Log.d("SignIn", "Signin successful")
//                                // Sign-in successful, navigate to the next screen or perform any required action.
//                                navController.navigate("dashboard")
//                            } else {
//                                // Sign-in failed
//                                authErrorMessage = "Sign-in failed. Please check your credentials."
//                            }
//                        } catch (e: Exception) {
//                            // Handle sign-in failure (e.g., display an error message)
//                            authErrorMessage = "Sign-in failed with exception: ${e.message}"
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = "Sign In")
//            }

            authErrorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Add the clickable TextButton for "Don't have an account? Sign up"
            TextButton(
                onClick = {
                    navController.navigate("signupScreen")
                    // Handle the Sign Up button click here
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Don't have an account? Sign up",
                    color = Color.White,
                )
            }


        }
    }
}

