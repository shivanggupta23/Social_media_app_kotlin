package com.example.news_app.view


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
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
import com.example.news_app.view_model.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel,
                 navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)$")
    val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$")
    val context = LocalContext.current

    Surface(
        color = Color.DarkGray, // Background color for dark theme
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
                text = "Sign Up",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                color = Color.White, // Text color for dark theme
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },// Text color for dark theme
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Bold),
                // Text color for dark theme

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                        isError = !email.matches(emailRegex),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Bold),// Text color for dark theme
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
//                keyboardActions = KeyboardActions(
//                    onDone = { keyboardController?.hide() } // Hide keyboard on Enter press
//                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                        isError = !password.matches(passwordRegex),
                singleLine = true
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.White) },
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Bold),
                // Text color for dark theme
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,

                    imeAction = ImeAction.Done
                ),

                keyboardActions = KeyboardActions(
                    onDone = {

                        // Handle sign-up action here
                    }
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            signUpViewModel.signUp(email, password,confirmPassword)
                            // Sign-in successful, navigate to the next screen or perform any required action.
                            navController.navigate("dashboard")
                        } catch (e: Exception) {
                            Toast.makeText(context, "Sign-in failed with exception: ${e.message}", Toast.LENGTH_SHORT).show()


                        }
                    }
//                    coroutineScope.launch {
//                        try {
//                            if (email.matches(emailRegex) && password.matches(passwordRegex) && password == confirmPassword) {
//                                val userCredential =
//                                    auth.createUserWithEmailAndPassword(email, password)
//                                val user: FirebaseUser? = userCredential.await().user
//                                if (user != null) {
//                                    Log.d("SignUp", "Registration successful")
//                                    navController.navigate("dashboard")
//                                    // Registration successful, navigate to the next screen or perform any required action.
//                                } else {
//                                    Log.d("SignUp", "Registration failed")
//                                }
//                            }
//                                else {
//                                    Log.d("SignUp", "Validation failed")
//                                Toast.makeText(context, "Email should contain @gmail", Toast.LENGTH_SHORT).show()
//                                Toast.makeText(context, "password should contain at least One UpperCase,One LowerCase and one Special Character", Toast.LENGTH_SHORT).show()
//                                }
//                                    // Registration failed
//
////                             else {
////                        Log.d("SignUp", "Passwords do not match")
////                                // Passwords do not match, show an error message
////                            }
//                        } catch (e: Exception) {
//                    Log.e("SignUp", "Registration failed with exception: ${e.message}")
//                            // Handle registration failure (e.g., display an error message)
//                        }
//                    }
                    // Handle sign-up button click here
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }

            // Add the clickable TextButton for "Already Have An Account? Sign in"
            TextButton(
                onClick = {
                    navController.navigate("signinui")
                    // Handle the Sign in button click here
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Already Have An Account? Sign in",
                    color = Color.White, // Text color for dark theme
                )
            }
        }
    }
}


//@Composable
//fun SignUpScreenPreview() {
//    // Initialize the NavController
//    val navController = rememberNavController()
//
//    // Set up the NavHost with the NavController
//    NavHost(
//        navController = navController,
//        startDestination = "signupScreen"
//    ) {
//        composable("signupScreen") {
//            SignUpScreen(signUpViewModel,
//                onSignUpSuccess,navController)
//        }
//        composable("signinScreen") {
//            // Create and display your SignInScreen composable here
//            // Replace with your actual SignInScreen implementation
//            // For example: SignInScreen(navController)
//        }
//    }
//    // Preview your sign-up screen
//
//}
