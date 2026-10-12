package com.example.highlowgamedemo.ui.screen

import android.app.AlertDialog
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun GameEndedDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        {
            TextButton(onClick = onConfirm) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Noooo")
            }
        },
        title = { Text("Game over") },
        text = {
            Text("Hurray! You won!")
        }
    )

}