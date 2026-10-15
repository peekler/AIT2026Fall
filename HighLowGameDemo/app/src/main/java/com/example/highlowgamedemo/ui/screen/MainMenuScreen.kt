package com.example.highlowgamedemo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainMenuScreen(
    onStartClick: (Int)->Unit
) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Row() {
            Button(onClick = {
                onStartClick(5)
            }) {
                Text("Easy")
            }
            Button(onClick = {

                onStartClick(20)
            }) {
                Text("Medium")
            }
            Button(onClick = {
                onStartClick(100)
            }) {
                Text("Hard")
            }
        }
        Button(onClick = {  }) {
            Text("Help")
        }
        Button(onClick = {  }) {
            Text("About")
        }

    }
}