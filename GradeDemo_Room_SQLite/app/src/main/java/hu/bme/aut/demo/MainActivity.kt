package hu.bme.aut.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.demo.data.AppDatabase
import hu.bme.aut.demo.ui.screen.GradeScreen
import hu.bme.aut.demo.ui.screen.GradeViewModel
import hu.bme.aut.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GradeScreen(
                        modifier = Modifier.padding(innerPadding),

                        gradeViewModel = viewModel(
                            factory = viewModelFactory {
                                initializer { GradeViewModel(
                                    AppDatabase.getDatabase(this@MainActivity).gradeDao()
                                ) } }
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxHeight().weight(1f),
            verticalArrangement = Arrangement.Center) {
            Button(onClick = {}, modifier= Modifier.fillMaxWidth()) { Text("Button") }
        }
        Column(modifier = modifier.fillMaxHeight().weight(1f)) {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().weight(1f)) {
                Text("Button")
            }
            Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().weight(1f)) {
                Text("Button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoTheme {
        Greeting("Android")
    }
}