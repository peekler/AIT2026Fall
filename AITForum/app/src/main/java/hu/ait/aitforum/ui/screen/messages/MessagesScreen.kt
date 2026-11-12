package hu.ait.aitforum.ui.screen.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MessagesScreen(
    onNewMessage : ()->Unit = {}
) {
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
        Column(modifier = Modifier.padding(innerPadding)) { }
        Text("Messages screen")
    }
}