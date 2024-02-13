package com.example.news_app.view


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news_app.data_classes.PostDataClass
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context

@Composable
fun CreatePost(
) {

    val database = Firebase.database
    val myRef = database.getReference("post")
    var imageUrl by remember { mutableStateOf("") }
    var userEmail by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Create New Post",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )


            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = imageUrl,
                onValueChange = { newValue ->
                    imageUrl = newValue
                },
                label = { Text(text = "Image URL") },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
//                onSubmit(imageUrl)
                    if (imageUrl.isNotEmpty()) {
                        val newPostRef = myRef.push()
//                        val postId = myRef.push().key
                        val postId = generateUniquePostId()

                        if (postId != null) {
                            val url = PostDataClass(imageUrl,userEmail)
                            myRef.child(postId).setValue(url).addOnSuccessListener {
                                imageUrl = ""
                                Toast.makeText(context, "Post Created", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {
                                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }


                        //for sending simple text
//                          val url = PostDataCLass(imageUrl.toString())
//                          myRef.child(imageUrl).setValue(url).addOnSuccessListener {
//                              imageUrl = ""
//                              Toast.makeText(context," Post Created",Toast.LENGTH_SHORT).show()
//                          }.addOnFailureListener{
//                              Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//                          }
//
                    } else {
                        Toast.makeText(context, "Please Insert the post link", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}

// Function to generate a unique post ID
private fun generateUniquePostId(): String {
    // Generate a unique ID using a combination of timestamp and a random value
    val timestamp = System.currentTimeMillis()
    val randomValue = (0..999999).random() // You can adjust the range as needed
    return "$timestamp$randomValue"
}

//@Composable
//@Preview
//fun ImageFormPreview() {
//    CreatePost { val navController = rememberNavController(),
//        navController = navController,
//        imageUrl ->
//
//        // Handle the submission of the image URL
//        // In a real app, you might want to perform network operations or display the image.
//        println("Submitted Image URL: $imageUrl")
//    }
//}



