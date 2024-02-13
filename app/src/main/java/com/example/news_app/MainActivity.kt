package com.example.news_app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.news_app.model.UserRepository
import com.example.news_app.ui.theme.NEWS_APPTheme
import com.example.news_app.view.CreatePost
import com.example.news_app.view.Dashboard
import com.example.news_app.view.SignInScreen
import com.example.news_app.view.SignUpScreen
import com.example.news_app.view_model.FetchRealtimeDatabase
import com.example.news_app.view_model.SignInViewModel
import com.example.news_app.view_model.SignUpViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var signUpViewModel: SignUpViewModel

    private var isFirebaseInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isFirebaseInitialized) {
            // Initialize Firebase only once
            FirebaseApp.initializeApp(this)
            isFirebaseInitialized = true
            Firebase.database.setPersistenceEnabled(true)
        }

        val userRepository = UserRepository()
        signInViewModel = SignInViewModel(userRepository)
        signUpViewModel = SignUpViewModel(userRepository)

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }


        setContent {
            NEWS_APPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Set up the NavHost with the NavController
                    NavHost(
                        navController = navController,
                        startDestination = "signupScreen"
                    ) {
                        composable("signupScreen") {
                            SignUpScreen(
                                signUpViewModel,
                                navController
                            )
                        }
                        composable("signinui") {
                            SignInScreen(
                                signInViewModel, // Provide the SignInViewModel
                                navController
                            )
                        }
                        composable("dashboard") {
                            Dashboard(
                                fetchRealtimeDatabase = FetchRealtimeDatabase(userRepository = UserRepository()),

                                navController = navController
                            )
                        }

//                            composable("dashboard") {
//                                Dashboard(navController)
//
//                            }
                            composable("newpost") {

                                CreatePost()
                            }
                        }
                    }
                }
            }
        }


    }
