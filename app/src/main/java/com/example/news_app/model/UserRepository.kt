package com.example.news_app.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    
//    auth.useAppLanguage()
//    auth.setPersistence(FirebaseAuth.Auth.Persistence.LOCAL)

    suspend fun signIn(email: String, password: String): Boolean {

        if (email.isEmpty() || password.isEmpty()) {
            throw IllegalArgumentException("Please fill in all fields.")
        }
        try {
            val userCredential = auth.signInWithEmailAndPassword(email, password)
            val user: FirebaseUser? = userCredential.await().user
            return user != null
        } catch (e: Exception) {
            // Handle sign-in failure (e.g., display an error message)
            throw IllegalStateException("Sign-in failed with exception: ${e.message}")
        }
    }

    suspend fun signUp(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            throw IllegalArgumentException("Please fill in all fields.")
        }
        if (password != confirmPassword) {
            throw IllegalArgumentException("Passwords do not match.")
        }
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
            throw IllegalArgumentException("Email should contain @gmail.")
        }
        if (!password.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$"))) {
            throw IllegalArgumentException("Password should contain at least one uppercase letter, one lowercase letter, one special character, and be at least 7 characters long.")
        }
        try {
            val userCredential = auth.createUserWithEmailAndPassword(email, password)
            val user: FirebaseUser? = userCredential.await().user
            return user != null
        } catch (e: Exception) {
            // Handle sign-up failure (e.g., display an error message)
            throw IllegalStateException("Sign-up failed with exception: ${e.message}")
        }
    }

    suspend fun getImageUrls(): List<String> {
        val dataRef = database.getReference("post") // Replace with your database reference
        val dataSnapshot = withContext(Dispatchers.IO){
            dataRef.get().await()
        }
//        val dataSnapshot = dataRef.get().await()
        val imageUrls = mutableListOf<String>()

        for (postSnapshot in dataSnapshot.children) {
            val imageUrl = postSnapshot.child("imageUrl").getValue(String::class.java)
            if (!imageUrl.isNullOrBlank()) {
                imageUrls.add(imageUrl)
            }
        }

        return imageUrls
    }
}

