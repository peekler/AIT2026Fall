package hu.ait.aitdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.aitdemo.ui.theme.AITDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AITDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "AIT Mobile Group",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var bgColor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = modifier.background(bgColor).fillMaxSize()
    ) {
        Text(
            text = "Hello $name!"
        )
        Text(
            text = "AIT Mobile Demo"
        )

        Button(
            onClick = {
                bgColor = Color.Green
            }
        ) {
            Text("Change background")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AITDemoTheme {
        Greeting("Android")
    }
}