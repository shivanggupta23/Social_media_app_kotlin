package com.example.news_app.view_model

import androidx.lifecycle.ViewModel
import com.example.news_app.model.UserRepository

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun signIn(email: String, password: String): Boolean {
        return userRepository.signIn(email, password)
    }
}

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    suspend fun signUp(email: String, password: String,confirmPassword: String): Boolean {
        return userRepository.signUp(email, password,confirmPassword)
    }
}

class FetchRealtimeDatabase(private val userRepository: UserRepository) : ViewModel() {
    suspend fun getImageUrls(): List<String>{
        return userRepository.getImageUrls()

    }
}




