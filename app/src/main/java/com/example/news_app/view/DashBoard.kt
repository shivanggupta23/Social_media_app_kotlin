package com.example.news_app.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.news_app.R
import com.example.news_app.data_classes.PostDataClass
import com.example.news_app.view_model.FetchRealtimeDatabase

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun Dashboard(fetchRealtimeDatabase: FetchRealtimeDatabase, navController: NavController) {
    var homeSelected by remember { mutableStateOf(false) }
    var profileSelected by remember { mutableStateOf(false) }
    val bottomAppBarHeight = 56.dp
    var postData by remember { mutableStateOf(emptyList<PostDataClass>()) } // Use PostDataClass here

    // Function to fetch post data from Firebase Database
    LaunchedEffect(key1 = true) {
        val data =  fetchRealtimeDatabase.getImageUrls()// Replace with the function to fetch post data
        postData = data.map { imageUrl->
            val userEmail = "shiv@gmail.com"
            PostDataClass(imageUrl,userEmail)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News Feed App",
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp
                    )
                },
                backgroundColor = Color.Magenta,
                actions = {
                    PlusIcon(onClick = {
                        // Navigate to the new post screen here
                        // You can use the NavController to navigate to the new screen
                        navController.navigate("newpost")
                    })
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Magenta
            ) {
                IconButton(
                    onClick = { homeSelected = !homeSelected },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_home_24),
                        modifier = Modifier.size(40.dp),
                        tint = Color.Black,
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = { profileSelected = !profileSelected },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_supervised_user_circle_24),
                        modifier = Modifier.size(38.dp),
                        contentDescription = null,
                        tint = Color.Black,
                    )
                }
            }
        },
        content = {
            BodyContent(bottomAppBarHeight, postData) // Pass the list of PostDataClass to BodyContent
        }
    )
}


@Composable
fun BodyContent(bottomAppBarHeight: Dp,postData:List<PostDataClass>) {
    var isLiked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val shareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "post shared", Toast.LENGTH_SHORT).show()

        }
    }

    // Create a state to track whether the comment popup should be shown
    var isCommentPopupVisible by remember { mutableStateOf(false) }

    // Create a state to track the index of the post being commented on
    var commentPostIndex by remember { mutableIntStateOf(-1) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(bottom = bottomAppBarHeight)
    ) {
        items(postData) { post ->
            val index = postData.indexOf(post)



            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.outline_insert_comment_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = post.userEmail,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(text = post.imageUrl)
//                    Log.d("MyTag","post.imageUrl")
                    Image(
                        painter = rememberAsyncImagePainter(model = post.imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )


                    // Footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                isLiked = !isLiked // Toggle the isLiked state on click
                            }


                        ) {

                            if (!isLiked) {
                                Icon(
                                    painter = painterResource(id = R.drawable.heart_thin_icon),
                                    contentDescription = null,
//                            tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.heart_red_svgrepo_com),
                                    contentDescription = null,
//                                   tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Like")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                // Show the comment popup when the comment icon is clicked
                                isCommentPopupVisible = true
                                commentPostIndex = index
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_comment),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Comment")
                        }
//                    }
//                }

                        // Show the comment popup if isCommentPopupVisible is true
                        if (isCommentPopupVisible && commentPostIndex >= 0) {
                            CommentPopup(
                                ignoredOnCommentPosted = {

                                },
                                ignoredOnDismiss = {
                                    // Dismiss the comment popup
                                    isCommentPopupVisible = false
                                    commentPostIndex = -1
                                }
                            )
                        }


//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.icons8_comment),
//                                contentDescription = null,
//                                tint = Color.Black,
//                                modifier = Modifier.size(30.dp)
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(text = "Comment")
//                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Check out this post: ${post.imageUrl}"
                                    )
                                    type = "text/plain"
                                }

                                // Create a chooser dialog to allow the user to select a sharing app
                                val shareIntent = Intent.createChooser(sendIntent, null)

                                // Launch the share intent using the launcher
                                shareLauncher.launch(shareIntent)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_share),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Share")
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun PlusIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ionic_ionicons_add),
            contentDescription = "Add",
            tint = Color.Black
        )
    }
}


//@Composable
//@Preview
//fun DashboardPreview() {
//    // Create a preview of the Dashboard composable
//    val navController = rememberNavController()
//
//    Dashboard(navController = navController)
//}
