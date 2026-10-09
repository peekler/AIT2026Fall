package com.example.statesdemoapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.statesdemoapplication.ui.theme.StatesDemoApplicationTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StatesDemoApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //Counter(modifier = Modifier.padding(innerPadding))
                    //TimeUpdater(Modifier.padding(innerPadding))
                    DisposeEffectDemo(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun LabeledButton(
    buttonText: String,
    data: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = data,
        )
        Button(
            onClick = onClick
        )
        {
            Text(text = buttonText)
        }
    }
}

@Composable
fun TimeUpdater(modifier: Modifier) {
    var time by rememberSaveable { mutableStateOf("Start time") }
    Column(modifier = modifier) {
    LabeledButton(
        "Click me to update the time",
        time,
        { time = Date(System.currentTimeMillis()).toString() },
        modifier
    )}

}


@Composable
fun Counter(modifier: Modifier = Modifier) {
    // Define a state variable for the count
    var count by remember { mutableStateOf(0) }

    // Use SideEffect to log the current value of count
    SideEffect {
        // Called on every recomposition
        Log.d("TAG_SIDE", "Side effect called")
    }

    LaunchedEffect(key1 = count) {
        // Called on every recomposition
        Log.d("TAG_SIDE", "launch effect called")
    }

    Column(modifier) {
        Button(onClick = { count++ }) {
            Text("Increase Count")
        }
        // With every state update, text is changed and recomposition is triggered
        Text("Counter ${count}")
    }
}

@Composable
fun DisposeEffectDemo(modifier: Modifier = Modifier) {
    // 0: main screen, 1: details screen
    var screenNr by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = screenNr == 0,
                    onClick = { screenNr = 0 },
                    label = { Text("Main") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Main") }
                )
                NavigationBarItem(
                    selected = screenNr == 1,
                    onClick = { screenNr = 1 },
                    label = { Text("Details") },
                    icon = { Icon(Icons.Default.Info, contentDescription = "Details") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (screenNr == 0) {
                MainScreen()
            } else {
                DetailsScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    LaunchedEffect(key1 = Unit) {
        Log.d("TAG_DISP", "MainScreen added")
    }

    DisposableEffect(key1 = Unit) {
        Log.d("TAG_DISP", "Parent rendered")

        onDispose {
            Log.d("TAG_DISP", "MainScreen removed")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text("MAIN SCREEN")
    }

}


@Composable

fun DetailsScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {

        Text("DETAILS SCREEN")

    }

}

