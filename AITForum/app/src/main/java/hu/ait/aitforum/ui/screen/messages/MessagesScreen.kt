package hu.ait.aitforum.ui.screen.messages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import hu.ait.aitforum.data.Post

@Composable
fun MessagesScreen(
    viewModel: MessagesScreenViewModel = viewModel(),
    onNewMessage : ()->Unit = {}
) {
    val postListState = viewModel.postsList().collectAsState(
        initial = MessagesUIState.Init
    )


    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    onNewMessage()
                }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (postListState.value) {
                is MessagesUIState.Init -> {Text("Init...")}
                is MessagesUIState.Loading-> {
                    CircularProgressIndicator()
                }
                is MessagesUIState.Error -> {
                    Text("Error...")
                }
                is MessagesUIState.Success -> {
                    LazyColumn {
                        items((postListState.value as MessagesUIState.Success).postList) {
                            PostCard(
                                post = it.post,
                                onRemoveItem = {
                                    viewModel.deletePost(it.postId)
                                },
                                currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                            )
                        }
                    }
                }
            }

        }

    }
}



@Composable
fun PostCard(
    post: Post,
    onRemoveItem: () -> Unit = {},
    currentUserId: String = ""
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = post.postTitle,
                    )
                    Text(
                        text = post.postBody,
                    )
                    Text(
                        text = post.postDate,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentUserId.equals(post.uid)) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                onRemoveItem()
                            },
                            tint = Color.Red
                        )
                    }
                }
            }

            if (post.imgUrl != "") {
                // HERE GOES THE IMAGE LATER
            }

        }
    }
}

