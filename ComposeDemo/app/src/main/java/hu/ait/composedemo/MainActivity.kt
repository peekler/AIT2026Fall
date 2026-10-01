package hu.ait.composedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.ait.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.flow.flow
import java.util.Date


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            ComposeDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "AIT Mobile Team",
                        modifier =
                            Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String,  modifier: Modifier = Modifier) {
    var currentDate
        by rememberSaveable { mutableStateOf("") }

    var userInput
            by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Time: $currentDate $userInput"
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "$userInput",
            onValueChange = { typedText ->
                userInput = typedText
            }
        )

        Button(
            onClick = {
                currentDate = Date(System.currentTimeMillis()).toString()
            }
        ) {
            Text("Click me")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoTheme {
        Greeting("Android")
    }
}

val myFun: (Int, Int)-> Int = {
    numa, numb ->
        demo(1, doThis = {})

        demo(1) {

        }

        val resutl = numa + 1
        resutl
}

// higher order function
fun demo(a: Int, doThis: ()->Unit): Unit {

}


